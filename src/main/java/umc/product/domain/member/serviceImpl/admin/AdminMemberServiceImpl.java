package umc.product.domain.member.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateMemberProfileRequest;
import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.jdbc.MemberJdbcRepository;
import umc.product.domain.member.repository.jpa.MemberJpaRepository;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberDslRepository memberDslRepository;
    private final MemberJdbcRepository memberJdbcRepository;

    private final MemberMapper memberMapper;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public List<Member> findExistedMemberList(AdminRegisterListRequest request) {
        return memberDslRepository.findByNameAndNickNameAndUniversity(request.registerMemberList());
    }

    @Override
    public Member toAdminMember(
            AdminSignUpRequest request,
            String avatarUrl,
            String universityName
    ) {
        return memberMapper.toAdminMemberEntity(request, avatarUrl, universityName);
    }

    @Override
    public Page<Member> findMemberListByFilter(
            Member member,
            Pageable pageable,
            Long semesterId,
            Role role,
            Part part
    ) {
        return memberDslRepository.findMemberListByFilter(pageable,member, semesterId, role, part);
    }


    @Override
    public Page<Member> findMembersBySearchString(
            Member member,
            Pageable pageable,
            String searchString
    ) {
        return memberDslRepository.findMembersBySearchString(member, pageable, searchString);
    }

    @Override
    public List<Member> toMemberFromNewRegisterMember(
            List<AdminRegisterListRequest.AdminRegisterMemberRequest> newMemberRequestList,
            List<University> universityList
    ) {
        return memberMapper.toNewMemberEntity(newMemberRequestList, universityList);
    }

    @Override
    public List<Member> saveRegisterNewMemberList(
            List<Member> memberList,
            List<SemesterPart> semesterPartList,
            List<SemesterPosition> semesterPositionList
    ) {
        return memberJdbcRepository.saveRegisterNewMemberList(memberList, semesterPartList, semesterPositionList);
    }

    @Override
    public void saveRegisterExistMemberList(
            List<SemesterPart> semesterPartList,
            List<SemesterPosition> semesterPositionList
    ) {
        memberJdbcRepository.saveRegisterExistMemberList(semesterPartList, semesterPositionList);
    }

    @Transactional
    @Override
    public void modifyMemberInfo(
            Member targetMember,
            University university,
            AdminUpdateMemberProfileRequest request,
            Map<Long, Semester> partSemesterMap,
            Map<Long, Semester> positionSemesterMap
    ) {
        targetMember.modifyProfile(request, university);

        //semesterPartId를 기준으로 request를 map으로 변환
        if(!request.semesterPartList().isEmpty()) {
            Map<Long, AdminUpdateMemberProfileRequest.AdminUpdateSemesterPartRequest> semesterPartRequestMap = request.semesterPartList().stream()
                    .collect(Collectors.toMap(AdminUpdateMemberProfileRequest.AdminUpdateSemesterPartRequest::semesterPartId, Function.identity()));

            targetMember.getMemberSemesterPart()
                    .forEach(semesterPart -> {
                        AdminUpdateMemberProfileRequest.AdminUpdateSemesterPartRequest matchingRequest = semesterPartRequestMap.get(semesterPart.getId());
                        if (matchingRequest != null)
                            semesterPart.updateSemesterPart(partSemesterMap.get(matchingRequest.semesterId()), matchingRequest.part());
                    });
        }

        if(!request.semesterPositionList().isEmpty()) {
            Map<Long, AdminUpdateMemberProfileRequest.AdminUpdateSemesterPositionRequest> semesterPositionRequestMap = request.semesterPositionList().stream()
                    .collect(Collectors.toMap(AdminUpdateMemberProfileRequest.AdminUpdateSemesterPositionRequest::semesterPositionId, Function.identity()));

            targetMember.getMemberSemesterPosition()
                    .forEach(semesterPosition -> {
                        AdminUpdateMemberProfileRequest.AdminUpdateSemesterPositionRequest matchingRequest = semesterPositionRequestMap.get(semesterPosition.getId());
                        if (matchingRequest != null) {
                            if (matchingRequest.centralPosition() == null && matchingRequest.universityPosition() == null) {
                                semesterPosition.updateSemesterPosition(positionSemesterMap.get(matchingRequest.semesterId()), "챌린저", null);
                            } else {
                                semesterPosition.updateSemesterPosition(positionSemesterMap.get(matchingRequest.semesterId()), matchingRequest.universityPosition(), matchingRequest.centralPosition());
                            }
                        }
                    });
        }
    }

    @Transactional
    @Override
    public void addSemesterPositionList(
            Member targetMember,
            List<SemesterPosition> semesterPositionList
    ) {
        targetMember.addSemesterPosition(semesterPositionList);
    }
    @Transactional
    @Override
    public void addSemesterPartList(
            Member targetMember,
            List<SemesterPart> semesterPartList
    ) {
        targetMember.addSemesterPart(semesterPartList);
    }

    @Override
    public List<Member> getMemberList(List<AdminStudyMemberRequest> members) {
        List<Member> memberList = members.stream()
                .map(request -> memberJpaRepository.findById(request.getMemberId())
                        .orElseThrow(() -> new RestApiException(MemberErrorStatus.MEMBER_NOT_FOUND)))
                .collect(Collectors.toList());
        if (memberList.size() > 5) {
            throw new RestApiException(StudyErrorStatus.STUDY_MEMBER_COUNT_EXCEEDED);
        }
        return memberList;
    }

    @Override
    public long countMemberByFilter(Member member, Long semesterId, Role role, Part part) {
        return memberDslRepository.countMemberByFilter(member, semesterId, role, part);
    }
}
