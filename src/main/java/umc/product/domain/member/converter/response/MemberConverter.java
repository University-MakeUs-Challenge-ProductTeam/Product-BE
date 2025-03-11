package umc.product.domain.member.converter.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchPageResponse;
import umc.product.domain.member.dto.response.admin.search.AdminProfileDetailResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.out.MemberOutResponse;
import umc.product.domain.member.dto.response.member.search.MemberProfileDetailResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.semester.dto.SemesterPositionResponse;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberConverter {
    public MemberIdResponse toMemberIdResponse(
            Long memberId
    ) {
        return MemberIdResponse.builder()
                .memberId(memberId)
                .build();
    }

    public MemberLoginResponse toLoginMemberResponse(
            final Member member,
            TokenInfo tokenInfo,
            Role role
    ) {
        return MemberLoginResponse.builder()
                .memberId(member.getId())
                .accessToken(tokenInfo.accessToken())
                .refreshToken(tokenInfo.refreshToken())
                .activeStatus(member.getStatus() != Status.WAITING_FOR_UPDATE)
                .role(role)
                .build();
    }

    public AdminMemberSearchPageResponse toAdminMemberSearchListResponse(
            Page<Member> memberList,
            Map<Long, String> codeMap
    ) {
        List<AdminMemberSearchPageResponse.AdminMemberSearchResponse> memberSearchResponse = memberList.getContent().stream()
                .map(member -> {
                    return toAdminMemberSearchResponse(member, codeMap);
                }).collect(Collectors.toList());

        return AdminMemberSearchPageResponse.builder()
                .memberList(new PageImpl<>(memberSearchResponse, memberList.getPageable(), memberList.getTotalElements()))
                .build();
    }

    public AdminMemberSearchPageResponse.AdminMemberSearchResponse toAdminMemberSearchResponse(
            Member member,
            Map<Long, String> codeMap
    ) {
        String code = codeMap.get(member.getId());
        return AdminMemberSearchPageResponse.AdminMemberSearchResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNickName())
                .universityName(member.getUniversity() != null ? member.getUniversity().getName() :null)
                .code(code)
                .semesterPartList(toMemberSemesterPartResponseList(member.getMemberSemesterPart()))
                .semesterPositionList(toMemberSemesterPositionResponse(member.getMemberSemesterPosition()))
                .outCount(member.getMemberOutList().size())
                .build();
    }

    public MemberProfileDetailResponse toMemberProfileDetailResponse(
            Member member
    ) {
        return MemberProfileDetailResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNickName())
                .university(member.getUniversity() != null ? member.getUniversity().getName() :null)
                .semesterPartList(toMemberSemesterPartResponseList(member.getMemberSemesterPart()))
                .semesterPositionList(toMemberSemesterPositionResponse(member.getMemberSemesterPosition()))
                .build();
    }

    public AdminProfileDetailResponse toAdminProfileDetailResponse(
            Member member
    ) {
        return AdminProfileDetailResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNickName())
                .university(member.getUniversity() != null ? member.getUniversity().getName() :null)
                .semesterPartList(toMemberSemesterPartResponseList(member.getMemberSemesterPart()))
                .semesterPositionList(toMemberSemesterPositionResponse(member.getMemberSemesterPosition()))
                .memberOutList(toMemberOutResponseList(member.getMemberOutList()))
                .build();
    }
    private List<MemberOutResponse> toMemberOutResponseList(
            List<MemberOut> memberOutList
    ) {
        return memberOutList.stream()
                .map(memberOut -> {
                    return MemberOutResponse.builder()
                            .outId(memberOut.getId())
                            .outReason(memberOut.getOutReason().getToKorean())
                            .build();
                }).collect(Collectors.toList());
    }

    //todo: 위치 리펙토링해야함
    private List<SemesterPartResponse> toMemberSemesterPartResponseList(
            List<SemesterPart> semesterPartList
    ) {
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
    private List<SemesterPositionResponse> toMemberSemesterPositionResponse(
            List<SemesterPosition> semesterPositionList
    ) {
        return semesterPositionList.stream()
                .map(semesterPosition -> {
                    return SemesterPositionResponse.builder()
                            .positionId(semesterPosition.getId())
                            .position(semesterPosition.getPosition())
                            .semesterName(semesterPosition.getSemester().getName())
                            .build();
                }).collect(Collectors.toList());
    }
}
