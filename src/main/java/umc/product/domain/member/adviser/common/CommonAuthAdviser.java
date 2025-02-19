package umc.product.domain.member.adviser.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.dto.request.common.CommonSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.service.common.MemberAuthService;
import umc.product.domain.member.service.common.MemberService;

@Component
@RequiredArgsConstructor
public class CommonAuthAdviser {
    private final MemberAuthService memberAuthService;

    private final MemberMapper memberMapper;
    public MemberIdResponse signUp(MultipartFile file, CommonSignUpRequest request){
        //FileCreateResponse fileCreateResponse = fileService.createFile("AVATAR-IMAGE", file);
        //University 가져오기
        //SemesterPart 생성
        Member member = memberMapper.toCommonMember(request, null);
        Member newMember = memberAuthService.signUp(member);
        return memberMapper.toMemberIdResponse(newMember.getId());
    }

    public MemberLoginResponse socialLogin(String accessToken, LoginType loginType) {
        return memberAuthService.socialLogin(accessToken, loginType);
    }

    public MemberGenerateTokenResponse regenerateToken(String refreshToken, Member member) {
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(Member member) {return memberAuthService.logout(member);}

    public MemberIdResponse withdrawal(Member member) {return memberAuthService.withdrawal(member);}


}
