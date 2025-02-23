package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.dto.response.member.MemberPositionResponse;
import umc.product.domain.member.dto.response.member.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.dto.SemesterResponse;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberConverter {
    public MemberIdResponse toMemberIdResponse(Long memberId) {
        return MemberIdResponse.builder()
                .memberId(memberId)
                .build();
    }

    public MemberLoginResponse toLoginMemberResponse(final Member member, TokenInfo tokenInfo, boolean isServiceMember, Role role) {
        return MemberLoginResponse.builder()
                .memberId(member.getId())
                .accessToken(tokenInfo.accessToken())
                .refreshToken(tokenInfo.refreshToken())
                .isServiceMember(isServiceMember)
                .role(role)
                .build();
    }

    public AdminMemberListResponse toAdminMemberListResponse(List<Member> memberList) {
        List<MemberSearchResponse> memberSearchResponse = memberList.stream()
                .map(this::toSearchMemberResponse).collect(Collectors.toList());
        return AdminMemberListResponse.builder()
                .memberList(memberSearchResponse)
                .build();
    }

    public MemberSearchResponse toSearchMemberResponse(Member member) {
        return MemberSearchResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNickName())
                .university(member.getUniversity().getName())
                .role(member.getRole().getToKorean())
                .status(member.getStatus())
                .memberSemesterList(toMemberSemesterResponseList(member.getMemberSemesterPart()))
                .memberPositionList(toMemberPositionResponse(member.getMemberSemesterPosition()))
                .build();
    }
    //todo: 위치 리펙토링해야함
    private List<SemesterResponse> toMemberSemesterResponseList(List<SemesterPart> semesterPartList) {
        return semesterPartList.stream()
                .map(semesterPart -> {
                    return SemesterResponse.builder()
                            .part(semesterPart.getPart())
                            .semester(semesterPart.getSemester().getName())
                            .build();
                }).collect(Collectors.toList());
    }
    //todo: 위치 리펙토링해야함
    private List<MemberPositionResponse> toMemberPositionResponse(List<SemesterPosition> semesterPositionList) {
        return semesterPositionList.stream()
                .map(semesterPosition -> {
                    return MemberPositionResponse.builder()
                            .position(semesterPosition.getPosition())
                            .semester(semesterPosition.getSemester().getName())
                            .build();
                }).collect(Collectors.toList());
    }
}
