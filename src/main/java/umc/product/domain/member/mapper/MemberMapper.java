package umc.product.domain.member.mapper;

import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import org.springframework.stereotype.Component;
import umc.product.global.dto.excel.ExcelMember;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public List<Member> toMember(List<ExcelMember> excelMemberList) {
        return excelMemberList.stream()
                .map(this::toMemberFromExcelMember)
                .collect(Collectors.toList());
    }
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl, String universityName){
        return Member.builder()
                .email(request.getEmail())
                .avatarUrl(avatarUrl)
                .name(universityName)
                .nickName(universityName)
                .clientId(request.getClientId())
                .loginType(LoginType.INTERNAL)
                .status(Status.ACTIVE)
                .role(Role.SCHOOL_ADMIN)
                .build();
    }

    public Member toMember(final String clientId, LoginType loginType){
        return Member.builder()
                .clientId(clientId)
                .loginType(loginType)
                .build();
    }

    private Member toMemberFromExcelMember(ExcelMember excelMember){
        return Member.builder()
                .name(excelMember.getName())
                .nickName(excelMember.getNickName())
                .role(excelMember.getRole())
                .university(excelMember.getUniversity())
                .build();
    }
}

