package umc.product.domain.member.serviceImpl.member;

import jakarta.transaction.Transactional;
import umc.product.domain.member.dto.request.admin.AdminProfileModifyRequest;
import umc.product.domain.member.dto.request.admin.AdminSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.AdminSemesterPositionRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.MemberCustomRepository;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;

    public Member findById(Long id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RestApiException(MemberErrorStatus.EMPTY_MEMBER));
    }

    // 회원 저장
    public Member saveEntity(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member modifyMyProfileAvatar(Member member, String avatarUrl) {
        member.setAvatarUrl(avatarUrl);
        return memberRepository.save(member);
    }

    @Override
    public List<Member> findWaitingMemberByUniversity(University university) {
        return memberCustomRepository.findWaitingMemberByUniversity(university);
    }

    @Override
    public List<Member> findWaitingMember() {
        return memberCustomRepository.findWaitingMember();
    }

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RestApiException(MemberErrorStatus.UNAUTHORIZED); // 로그인 안함
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof PrincipalDetails) {
            return ((PrincipalDetails) principal).getMember();
        }

        throw new RestApiException(MemberErrorStatus.AUTHENTICATION_FAILED); // 로그인 정보를 확인할 수 없음

    }
}
