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
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.service.SemesterPositionService;
import umc.product.domain.semester.service.SemesterService;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.jwt.JwtProvider;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static umc.product.domain.semester.status.SemesterErrorStatus.EXIST_SEMESTER;

@Component
@RequiredArgsConstructor
public class MemberAuthAdviser {
    private final MemberAuthService memberAuthService;
    private final MemberService memberService;
    private final SemesterService semesterService;
    private final SemesterPartService semesterPartService;
    private final SemesterPositionService semesterPositionService;
    private final MemberRefreshTokenService memberRefreshTokenService;
    private final FileService fileService;

    private final JwtProvider jwtProvider;

    private final MemberConverter memberConverter;
    private final SemesterPartMapper semesterPartMapper;
    public MemberLoginResponse signUp(
            MemberSignUpRequest request
    ){
        //1차 MVP이후 회원가입 시 프로필 사진 설정 생기면 사용
        /*if(file != null) {
            FileCreateResponse fileCreateResponse = fileService.createFile("avatar", file);
        }*/
        Member member = memberService.findByIdNotFetchLoginInfo(request.memberId());
        member.updateProfile(request);

        List<SemesterPart> semesterPartList = new ArrayList<>();

        if(!request.semesterPartList().isEmpty()) {
            //학기 찾기
            List<Semester> semesterList = semesterService.findSemesterListForSignup(request.semesterPartList());
            //이미 해당 기수에 파트가 존재하는지 확인
            if(semesterPartService.existSemesterPart(semesterList, member)) {
                throw new RestApiException(EXIST_SEMESTER);
            }
            //학기를 기반으로 학기/파트 생성
            semesterPartList = semesterPartMapper.toSemesterPart(semesterList, request.semesterPartList(), member);

            Map<Long, SemesterPosition> semesterPositionMap = semesterPositionService.findSemesterPositionMapByMemberId(member.getId());

            semesterList.forEach(semester -> {
                SemesterPosition semesterPosition = semesterPositionMap.get(semester.getId());

                if (semesterPosition != null) {  // 이미 해당 기수 직책이 설정되어 있음 (OB 유저)
                    if (semesterPosition.getUniversityPosition() == null) {
                        semesterPosition.updateSemesterPosition(semester, "챌린저", semesterPosition.getCentralPosition());
                    }
                } else {  // 해당 기수 직책이 설정되지 않은 경우 (신규 유저)
                    semesterPosition = SemesterPosition.builder()
                            .member(member)
                            .centralPosition(null)
                            .universityPosition("챌린저")
                            .semester(semester)
                            .build();
                    member.addSemesterPosition(List.of(semesterPosition)); // member에 추가
                }
            });
        }

        Member newMember = memberAuthService.signUp(request.clientId(), member, semesterPartList, "https://umc-offcial-product.s3.ap-northeast-2.amazonaws.com/avatar/default-avatar-img_5182333b-1626-4ddf-b5ab-646c916253cf.jpg");
        TokenInfo tokenInfo = jwtProvider.generateToken(newMember.getId().toString(), newMember.getRole().toString());
        return memberConverter.toLoginMemberResponse(newMember, tokenInfo, newMember.getRole());
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
