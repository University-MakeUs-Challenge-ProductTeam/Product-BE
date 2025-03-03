package umc.product.domain.member.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.AdminProfileModifyRequest;
import umc.product.domain.member.dto.request.admin.AdminSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.AdminSemesterPositionRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberCustomRepository;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;
import umc.product.global.dto.excel.ExcelMember;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberCustomRepository memberCustomRepository;

    private final MemberMapper memberMapper;

    @Override
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl, String universityName) {
        return memberMapper.toAdminMember(request, avatarUrl, universityName);
    }

    @Override
    public List<Member> findMembers(Member member, Pageable pageable, String semester, Role role, Part part) {
        return memberCustomRepository.findMembers(pageable,member, semester, role, part);
    }


    @Override
    public List<Member> findMembersBySearchString(Member member, String searchString) {
        return memberCustomRepository.findMembersBySearchString(member, searchString);
    }

    @Override
    public List<Member> toMemberFromExcelMember(List<ExcelMember> excelMemberList) {
        return memberMapper.toMember(excelMemberList);
    }

    @Override
    public void saveRegisterMembers(List<Member> memberList, List<SemesterPosition> semesterPositionList) {
        memberCustomRepository.saveRegisterMembers(memberList, semesterPositionList);
    }

    @Transactional
    @Override
    public void modifyMemberInfo(Member targetMember, University university, AdminProfileModifyRequest request, Map<Long, Semester> partSemesterMap, Map<Long, Semester> positionSemesterMap) {
        targetMember.modifyProfile(request, university);

        Map<Long, AdminSemesterPartRequest> semesterPartRequestMap = request.getSemesterPartList().stream()
                .collect(Collectors.toMap(AdminSemesterPartRequest::getSemesterPartId, Function.identity()));

        targetMember.getMemberSemesterPart()
                .forEach(semesterPart -> {
                    AdminSemesterPartRequest matchingRequest = semesterPartRequestMap.get(semesterPart.getId());
                    if(matchingRequest != null) semesterPart.updateSemesterPart(partSemesterMap.get(matchingRequest.getSemesterId()), matchingRequest.getPart());
                });

        Map<Long, AdminSemesterPositionRequest> semesterPositionRequestMap = request.getSemesterPositionList().stream()
                .collect(Collectors.toMap(AdminSemesterPositionRequest::getSemesterPositionId, Function.identity()));

        targetMember.getMemberSemesterPosition()
                .forEach(semesterPosition -> {
                    AdminSemesterPositionRequest matchingRequest = semesterPositionRequestMap.get(semesterPosition.getId());
                    if(matchingRequest != null) semesterPosition.updateSemesterPosition(partSemesterMap.get(matchingRequest.getSemesterId()), matchingRequest.getPosition());
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
