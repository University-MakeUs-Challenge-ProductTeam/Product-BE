package umc.product.domain.member.converter.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchPageResponse;
import umc.product.domain.member.dto.response.admin.search.AdminProfileDetailResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.out.MemberOutResponse;
import umc.product.domain.member.dto.response.member.search.MemberParticipateInfoResponse;
import umc.product.domain.member.dto.response.member.search.MemberProfileResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.semester.dto.SemesterPositionResponse;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .nickName(member.getNickName())
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
                .status(member.getStatus())
                .semesterPartList(toMemberSemesterPartResponseList(member.getMemberSemesterPart()))
                .semesterPositionList(toMemberSemesterPositionResponse(member.getMemberSemesterPosition()))
                .outCount(member.getMemberOutList().size())
                .build();
    }

    public MemberParticipateInfoResponse toMemberParticipateInfoResponse(
            Long memberId,
            Map<Long, SemesterPart> semesterPartMap,
            Map<Long, SemesterPosition> semesterPositionMap
    ) {
        return MemberParticipateInfoResponse.builder()
                .memberId(memberId)
                .semesterList(toMemberSemesterInfoResponseList(semesterPartMap, semesterPositionMap))
                .build();
    }

    private List<MemberParticipateInfoResponse.MemberSemesterInfoResponse> toMemberSemesterInfoResponseList(
            Map<Long, SemesterPart> semesterPartMap,
            Map<Long, SemesterPosition> semesterPositionMap
    ) {

        Set<Long> allSemesterIdSet = new HashSet<>();
        allSemesterIdSet.addAll(semesterPartMap.keySet());
        allSemesterIdSet.addAll(semesterPositionMap.keySet());

        return allSemesterIdSet.stream()
                .flatMap(semesterId -> {
                    SemesterPart semesterPart = semesterPartMap.get(semesterId);        //파트는 반드시 한 기수당 하나
                    SemesterPosition semesterPosition = semesterPositionMap.get(semesterId);  //최대 2개임(챌린저면서 운영진이면서 중앙인 경우는 제외)

                    if (semesterPosition == null) {
                        if(semesterPart == null) {      //조회할 대상이 없음
                            return Stream.empty();
                        }
                        return Stream.of(
                                MemberParticipateInfoResponse.MemberSemesterInfoResponse.builder()
                                        .semesterId(semesterId)
                                        .semester(semesterPart.getSemester().getName())
                                        .part(semesterPart.getPart())
                                        .centralPosition(null)
                                        .universityPosition(null)
                                        .build()
                        );
                    }
                    //position 존재

                    return Stream.of(
                            MemberParticipateInfoResponse.MemberSemesterInfoResponse.builder()
                                    .semesterId(semesterId)
                                    .semester(semesterPosition.getSemester().getName())
                                    .part(semesterPart != null ? semesterPart.getPart() : null)
                                    .centralPosition(semesterPosition.getCentralPosition())
                                    .universityPosition(semesterPosition.getUniversityPosition())
                                    .build()
                    );

                })
                .collect(Collectors.toList());
    }

    public MemberProfileResponse toMemberProfileResponse(
            Member member
    ) {
        return MemberProfileResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNickName())
                .university(member.getUniversity() != null ? member.getUniversity().getName() :null)
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
                .status(member.getStatus())
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

    private List<SemesterPositionResponse> toMemberSemesterPositionResponse(
            List<SemesterPosition> semesterPositionList
    ) {
        return semesterPositionList.stream()
                .map(semesterPosition -> {
                    return SemesterPositionResponse.builder()
                            .positionId(semesterPosition.getId())
                            .universityPosition(semesterPosition.getUniversityPosition())
                            .centralPosition(semesterPosition.getCentralPosition())
                            .semesterName(semesterPosition.getSemester().getName())
                            .build();
                }).collect(Collectors.toList());
    }
}
