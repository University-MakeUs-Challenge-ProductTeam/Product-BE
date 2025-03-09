package umc.product.domain.member.adviser.member;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.file.dto.FileCreateResponse;
import umc.product.domain.file.service.FileService;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.member.auth.MemberSignUpRequest;
import umc.product.domain.member.dto.response.member.auth.MemberCreateTokenResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
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
    private final FileService fileService;

    private final MemberConverter memberConverter;
    private final SemesterPartMapper semesterPartMapper;
    public MemberIdResponse signUp(
            MultipartFile file,
            MemberSignUpRequest request
    ){
        //1차 MVP이후 회원가입 시 프로필 사진 설정 생기면 사용
        if(file != null) {
            FileCreateResponse fileCreateResponse = fileService.createFile("avatar", file);
        }
        Member member = memberService.findById(request.memberId());
        member.updateProfile(request);
        //학기 찾기
        List<Semester> semesterList = semesterService.findSemesterListForSignup(request.semesterPartList());
        //학기를 기반으로 학기/파트 생성
        List<SemesterPart> semesterPartList = semesterPartMapper.toSemesterPart(semesterList, request.semesterPartList(), member);

        Member newMember = memberAuthService.signUp(request.clientId(), member, semesterPartList, "https://umc-offcial-product.s3.ap-northeast-2.amazonaws.com/avatar/default-avatar-img_5182333b-1626-4ddf-b5ab-646c916253cf.jpg");
        return memberConverter.toMemberIdResponse(newMember.getId());
    }

    public MemberLoginResponse socialLogin(
            String accessToken,
            LoginType loginType
    ) {
        return memberAuthService.socialLogin(accessToken, loginType);
    }

    public MemberCreateTokenResponse regenerateToken(
            String refreshToken
    ) {
        Claims claims = memberRefreshTokenService.getClaims(refreshToken);
        Long memberId = Long.parseLong(claims.get("memberId").toString());
        Member member = memberService.findById(memberId);
        return memberAuthService.generateNewAccessToken(refreshToken, member);
    }

    public MemberIdResponse logout(
            Member member
    ) {
        return memberAuthService.logout(member);
    }

    public MemberIdResponse withdrawal(
            Member member
    ) {
        return memberAuthService.withdrawal(member);
    }
}
