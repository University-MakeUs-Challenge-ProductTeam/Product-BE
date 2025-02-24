package umc.product.domain.member.adviser.member;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;
import umc.product.domain.member.dto.response.member.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.service.common.MemberAuthService;
import umc.product.domain.member.service.common.MemberRefreshTokenService;
import umc.product.domain.member.service.common.MemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.service.SemesterPositionService;
import umc.product.domain.semester.service.SemesterService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberAuthAdviser {
    private final MemberAuthService memberAuthService;
    private final MemberService memberService;
    private final UniversityService universityService;
    private final SemesterService semesterService;
    private final SemesterPositionService semesterPositionService;
    private final MemberRefreshTokenService memberRefreshTokenService;

    private final MemberConverter memberConverter;
    private final SemesterPartMapper semesterPartMapper;
    public MemberIdResponse signUp(MultipartFile file, MemberSignUpRequest request){
        //FileCreateResponse fileCreateResponse = fileService.createFile("AVATAR-IMAGE", file);
        //학교 찾기
        University university = universityService.findUniversity(request.getUniversity());
        //학기 찾기
        List<Semester> semesterList = semesterService.findSemesters(request.getSemesterList());
        //멤버 생성
        Member member = memberService.toCommonMember(request, null);
        //학기를 기반으로 학기/파트 생성
        List<SemesterPart> semesterPartList = semesterPartMapper.toSemesterPart(semesterList, request.getSemesterList(), member);
        //학기를 기반으로 학기/직책 생성
        List<SemesterPosition> semesterPositionList = semesterPositionService.toSemesterPosition(member, semesterList.get(0), request.getMemberCodePropertiesList());
        //겸직이 가능하기에 가장 높은 Role 선택
        Optional<MemberCodePropertiesResponse> highestRole = request.getMemberCodePropertiesList().stream()
                .min(Comparator.comparingInt(m -> m.getRole().getPriority()));

        Member newMember = memberAuthService.signUp(member, university, semesterPartList, semesterPositionList, highestRole.get().getRole());
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
