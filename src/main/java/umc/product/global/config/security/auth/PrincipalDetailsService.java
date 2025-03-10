package umc.product.global.config.security.auth;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.serviceImpl.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberServiceImpl memberService;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try { //username이 의미 하는 것은 엔티티의 id
             // 그래서 Long으로 변환
            Long userId = Long.parseLong(username); // Long으로 변환
            Member memberEntity = memberService.findById(userId); // 서비스에서 회원 조회
            return new PrincipalDetails(memberEntity); // PrincipalDetails 반환
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid ID format", e); // 잘못된 ID 형식 예외 처리
        }
    }
}*/
