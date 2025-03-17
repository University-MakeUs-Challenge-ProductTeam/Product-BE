package umc.product.domain.member.adviser.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.dto.FileCreateResponse;
import umc.product.domain.file.service.FileService;
import umc.product.domain.member.converter.response.MemberCodeConverter;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.response.member.code.MemberCodeVerifyResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.search.MemberParticipateInfoResponse;
import umc.product.domain.member.dto.response.member.search.MemberProfileResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.member.MemberCodeService;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.service.SemesterPositionService;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberAdviser {
    private final MemberService memberService;
    private final MemberCodeService memberCodeService;
    private final SemesterPartService semesterPartService;
    private final SemesterPositionService semesterPositionService;
    private final FileService fileService;

    private final MemberConverter memberConverter;
    private final MemberCodeConverter memberCodeConverter;

    public MemberCodeVerifyResponse verifyAppCode(
            String code
    ) {
        Long memberId = memberCodeService.verifyAppCode(code);
        Member member = memberService.findByIdNotFetchLoginInfo(memberId);
        return memberCodeConverter.toMemberCodeVerifyResponse(member);
    }

    public MemberProfileResponse getProfile(
            Long memberId
    ) {
        Member member = memberService.findById(memberId);
        return  memberConverter.toMemberProfileResponse(member);
    }

    public MemberParticipateInfoResponse getParticipateInfo(
            Long memberId
    ) {
        memberService.existById(memberId);  //검색하려는 사용자 검증
        Map<Long, SemesterPart> semesterPartMap = semesterPartService.findSemesterPartMapByMemberId(memberId);
        Map<Long, SemesterPosition> semesterPositionMap = semesterPositionService.findSemesterPositionMapByMemberId(memberId);
        return  memberConverter.toMemberParticipateInfoResponse(memberId, semesterPartMap, semesterPositionMap);
    }

    public MemberIdResponse modifyMyProfileAvatar(
            Member member,
            MultipartFile file
    ) {
        FileCreateResponse fileCreateResponse = fileService.createFile("avatar", file);
        memberService.modifyMyProfileAvatar(member, fileCreateResponse.getUrl());
        return  memberConverter.toMemberIdResponse(member.getId());
    }
}
