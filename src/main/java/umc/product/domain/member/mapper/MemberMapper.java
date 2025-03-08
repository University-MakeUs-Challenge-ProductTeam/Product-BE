package umc.product.domain.member.mapper;

import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterMemberRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import org.springframework.stereotype.Component;
import umc.product.domain.university.entity.University;
import umc.product.global.dto.excel.ExcelMember;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public List<Member> toMember(AdminRegisterRequest request, List<University> universityList) {
        Map<String, University> universityMap = universityList.stream()
                .collect(Collectors.toMap(University::getName, university -> university));

        return request.registerMemberList().stream()
                .map(request1 -> toMemberFromExcelMember(request1, universityMap))
                .collect(Collectors.toList());
    }
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl, String universityName){
        return Member.builder()
                .email(request.email())
                .avatarUrl(avatarUrl)
                .name(universityName)
                .nickName(universityName)
                .loginType(LoginType.INTERNAL)
                .status(Status.ACTIVE)
                .role(Role.SCHOOL_ADMIN)
                .build();
    }

    private Member toMemberFromExcelMember(AdminRegisterMemberRequest request, Map<String, University> universityMap){
        Role role = Role.valueOf(determineRole(request.centralPosition(), request.universityPosition()));
        return Member.builder()
                .name(request.name())
                .nickName(request.nickName())
                .role(role)
                .university(universityMap.get(request.universityName()))
                .build();
    }

    private String determineRole(String centralPosition, String universityPosition) {
        if (centralPosition == null && universityPosition == null) {
            return "CHALLENGER";
        }
        if (centralPosition != null && (centralPosition.equals("총괄") || centralPosition.equals("부총괄"))) {
            return "ADMIN";
        }
        if (centralPosition != null) {
            return "CENTRAL_ADMIN";
        }
        if (universityPosition != null && universityPosition.equals("회장") || universityPosition.equals("부회장")) {
            return "SCHOOL_ADMIN";
        }
        if(universityPosition != null) {
            return "UNIVERSITY_STAFF";
        }
        return "CHALLENGER";
    }
}

