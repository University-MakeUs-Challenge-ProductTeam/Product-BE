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
import umc.product.domain.member.dto.response.member.search.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.member.MemberCodeService;
import umc.product.domain.member.service.member.MemberService;

@Component
@RequiredArgsConstructor
public class MemberMemberAdviser {
    private final MemberService memberService;
    private final MemberCodeService memberCodeService;
    private final FileService fileService;

    private final MemberConverter memberConverter;
    private final MemberCodeConverter memberCodeConverter;

    public MemberCodeVerifyResponse verifyAppCode(
            String code
    ) {
        Long memberId = memberCodeService.verifyAppCode(code);
        Member member = memberService.findById(memberId);
        return memberCodeConverter.toMemberCodeVerifyResponse(member);
    }

    public MemberSearchResponse getProfile(
            Long memberId
    ) {
        Member member = memberService.findById(memberId);
        return  memberConverter.toSearchMemberResponse(member);
    }

    public MemberIdResponse modifyMyProfileAvatar(
            Member member,
            MultipartFile file
    ) {
        FileCreateResponse fileCreateResponse = fileService.createFile("avatar", file);
        Member modifyMember = memberService.modifyMyProfileAvatar(member, fileCreateResponse.getUrl());
        return  memberConverter.toMemberIdResponse(modifyMember.getId());
    }
}
