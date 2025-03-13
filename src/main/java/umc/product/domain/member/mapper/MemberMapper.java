package umc.product.domain.member.mapper;

import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import org.springframework.stereotype.Component;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static umc.product.domain.member.status.MemberErrorStatus.NULL_VALUE_IN_MEMBER;
import static umc.product.domain.university.status.UniversityErrorStatus.NOT_FOUND_UNIVERSITY;


@Component
public class MemberMapper {

    public List<Member> toNewMemberEntity(
            List<AdminRegisterListRequest.AdminRegisterMemberRequest> request,
            List<University> universityList
    ) {
        //map으로 변환하여 빠르게 university를 찾음
        Map<String, University> universityMap = universityList.stream()
                .collect(Collectors.toMap(University::getName, university -> university));

        return IntStream.range(0, request.size())
                .mapToObj(i -> {
                        return toMemberEntityFromExcelMember(request.get(i), universityMap, i);
                })
                .collect(Collectors.toList());
    }
    public Member toAdminMemberEntity(
            AdminSignUpRequest request,
            String avatarUrl,
            String universityName
    ){
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

    private Member toMemberEntityFromExcelMember(
            AdminRegisterListRequest.AdminRegisterMemberRequest request,
            Map<String, University> universityMap,
            int index
    ){
        Role role = Role.valueOf(determineRole(request.centralPosition(), request.universityPosition()));
        if(request.name() == null || request.nickName() == null || request.universityName() ==null){
            throw new RestApiException(NULL_VALUE_IN_MEMBER);
        }
        University university = Optional.ofNullable(universityMap.get(request.universityName()))
                .orElseThrow(() -> new RestApiException(NOT_FOUND_UNIVERSITY));

        return Member.builder()
                .name(request.name())
                .nickName(request.nickName())
                .role(role)
                .university(university)
                .build();
    }

    /**
     *
     * @param centralPosition 총괄/부총괄/... etc(중앙 직책)
     * @param universityPosition 회장/부회장/~파트장/... etc(교내 운영진 직책)
     * @return Role (권한)
     */
    private String determineRole(
            String centralPosition,
            String universityPosition
    ) {
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

