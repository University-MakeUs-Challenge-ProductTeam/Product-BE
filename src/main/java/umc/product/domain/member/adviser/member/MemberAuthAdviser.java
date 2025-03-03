package umc.product.domain.member.adviser.member;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.dto.response.member.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.service.member.MemberAuthService;
import umc.product.domain.member.service.member.MemberRefreshTokenService;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.service.SemesterService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberAuthAdviser {
    private final MemberAuthService memberAuthService;
    private final MemberService memberService;
    private final SemesterService semesterService;
    private final MemberRefreshTokenService memberRefreshTokenService;

    private final MemberConverter memberConverter;
    private final SemesterPartMapper semesterPartMapper;
    public MemberIdResponse signUp(MultipartFile file, MemberSignUpRequest request){
        //FileCreateResponse fileCreateResponse = fileService.createFile("AVATAR-IMAGE", file);
        Member member = memberService.findById(request.getMemberId());
        member.updateProfile(request);
        //학기 찾기
        List<Semester> semesterList = semesterService.findSemesterListForSignup(request.getSemesterList());
        //학기를 기반으로 학기/파트 생성
        List<SemesterPart> semesterPartList = semesterPartMapper.toSemesterPart(semesterList, request.getSemesterList(), member);

        Member newMember = memberAuthService.signUp(member, semesterPartList);
        return memberConverter.toMemberIdResponse(newMember.getId());
    }

    public MemberLoginResponse socialLogin(String accessToken, LoginType loginType) {
        return memberAuthService.socialLogin(accessToken, loginType);
    }

    public MemberGenerateTokenResponse regenerateToken(String refreshToken) {
        Claims claims = memberRefreshTokenService.getClaims(refreshToken);
        Long memberId = Long.parseLong(claims.get("memberId").toString());
        Member member = memberService.findById(memberId);
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(Member member) {return memberAuthService.logout(member);}

    public MemberIdResponse withdrawal(Member member) {return memberAuthService.withdrawal(member);}


}
