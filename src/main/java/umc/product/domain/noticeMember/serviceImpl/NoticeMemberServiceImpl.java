package umc.product.domain.noticeMember.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.noticeMember.repository.NoticeMemberRepository;
import umc.product.domain.noticeMember.service.NoticeMemberService;

@Service
@RequiredArgsConstructor
public class NoticeMemberServiceImpl implements NoticeMemberService {
    private final NoticeMemberRepository noticeMemberRepository;
}
