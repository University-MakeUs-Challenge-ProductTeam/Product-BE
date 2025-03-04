package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchListResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.search.MemberOutResponse;
import umc.product.domain.member.dto.response.member.search.MemberSearchResponse;
import umc.product.domain.member.dto.response.member.search.MemberSemesterPositionResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;
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

    public MemberLoginResponse toLoginMemberResponse(final Member member, TokenInfo tokenInfo, Role role) {
        return MemberLoginResponse.builder()
                .memberId(member.getId())
                .accessToken(tokenInfo.accessToken())
                .refreshToken(tokenInfo.refreshToken())
                .activeStatus(member.getStatus() != Status.WAITING_FOR_UPDATE)
                .role(role)
                .build();
    }

    public AdminMemberSearchListResponse toAdminMemberListResponse(List<Member> memberList) {
        List<MemberSearchResponse> memberSearchResponse = memberList.stream()
                .map(this::toSearchMemberResponse).collect(Collectors.toList());
        return AdminMemberSearchListResponse.builder()
                .memberList(memberSearchResponse)
                .build();
    }

    public MemberSearchResponse toSearchMemberResponse(Member member) {
        return MemberSearchResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNickName())
                .university(member.getUniversity() != null ? member.getUniversity().getName() :null)
                .role(member.getRole().getToKorean())
                .status(member.getStatus())
                .memberSemesterPartList(toMemberSemesterPartResponseList(member.getMemberSemesterPart()))
                .memberSemesterPositionList(toMemberSemesterPositionResponse(member.getMemberSemesterPosition()))
                .memberOutList(toMemberOutResponseList(member.getMemberOutList()))
                .build();
    }
    private List<MemberOutResponse> toMemberOutResponseList(List<MemberOut> memberOutList) {
        return memberOutList.stream()
                .map(memberOut -> {
                    return MemberOutResponse.builder()
                            .outId(memberOut.getId())
                            .outReason(memberOut.getOutReason().getToKorean())
                            .build();
                }).collect(Collectors.toList());
    }

    //todo: 위치 리펙토링해야함
    private List<SemesterPartResponse> toMemberSemesterPartResponseList(List<SemesterPart> semesterPartList) {
        return semesterPartList.stream()
                .map(semesterPart -> {
                    return SemesterPartResponse.builder()
                            .semesterPartId(semesterPart.getId())
                            .part(semesterPart.getPart())
                            .semester(semesterPart.getSemester().getName())
                            .build();
                }).collect(Collectors.toList());
    }
    //todo: 위치 리펙토링해야함
    private List<MemberSemesterPositionResponse> toMemberSemesterPositionResponse(List<SemesterPosition> semesterPositionList) {
        return semesterPositionList.stream()
                .map(semesterPosition -> {
                    return MemberSemesterPositionResponse.builder()
                            .positionId(semesterPosition.getId())
                            .position(semesterPosition.getPosition())
                            .semesterName(semesterPosition.getSemester().getName())
                            .build();
                }).collect(Collectors.toList());
    }
}
