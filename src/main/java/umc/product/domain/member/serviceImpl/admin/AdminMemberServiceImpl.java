package umc.product.domain.member.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateMemberProfileRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateSemesterPositionRequest;
import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.jdbc.MemberJdbcRepository;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberDslRepository memberDslRepository;
    private final MemberJdbcRepository memberJdbcRepository;

    private final MemberMapper memberMapper;

    @Override
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl, String universityName) {
        return memberMapper.toAdminMember(request, avatarUrl, universityName);
    }

    @Override
    public List<Member> findMembers(Member member, Pageable pageable, Long semesterId, Role role, Part part) {
        return memberDslRepository.findMemberList(pageable,member, semesterId, role, part);
    }


    @Override
    public List<Member> findMembersBySearchString(Member member, String searchString) {
        return memberDslRepository.findMembersBySearchString(member, searchString);
    }

    @Override
    public List<Member> toMemberFromExcelMember(AdminRegisterRequest request, List<University> universityList) {
        return memberMapper.toMember(request, universityList);
    }

    @Override
    public List<Member> saveRegisterMembers(List<Member> memberList, List<SemesterPart> semesterPartList, List<SemesterPosition> semesterPositionList) {
        return memberJdbcRepository.saveRegisterMembers(memberList, semesterPartList, semesterPositionList);
    }

    @Transactional
    @Override
    public void modifyMemberInfo(Member targetMember, University university, AdminUpdateMemberProfileRequest request, Map<Long, Semester> partSemesterMap, Map<Long, Semester> positionSemesterMap) {
        targetMember.modifyProfile(request, university);

        Map<Long, AdminUpdateSemesterPartRequest> semesterPartRequestMap = request.semesterPartList().stream()
                .collect(Collectors.toMap(AdminUpdateSemesterPartRequest::semesterPartId, Function.identity()));

        targetMember.getMemberSemesterPart()
                .forEach(semesterPart -> {
                    AdminUpdateSemesterPartRequest matchingRequest = semesterPartRequestMap.get(semesterPart.getId());
                    if(matchingRequest != null) semesterPart.updateSemesterPart(partSemesterMap.get(matchingRequest.semesterId()), matchingRequest.part());
                });

        Map<Long, AdminUpdateSemesterPositionRequest> semesterPositionRequestMap = request.semesterPositionList().stream()
                .collect(Collectors.toMap(AdminUpdateSemesterPositionRequest::semesterPositionId, Function.identity()));

        targetMember.getMemberSemesterPosition()
                .forEach(semesterPosition -> {
                    AdminUpdateSemesterPositionRequest matchingRequest = semesterPositionRequestMap.get(semesterPosition.getId());
                    if(matchingRequest != null) semesterPosition.updateSemesterPosition(partSemesterMap.get(matchingRequest.semesterId()), matchingRequest.position());
                });
    }

    @Transactional
    @Override
    public void addSemesterPositionList(Member targetMember, List<SemesterPosition> semesterPositionList) {
        targetMember.addSemesterPosition(semesterPositionList);
    }
    @Transactional
    @Override
    public void addSemesterPartList(Member targetMember, List<SemesterPart> semesterPartList) {
        targetMember.addSemesterPart(semesterPartList);
    }
}
