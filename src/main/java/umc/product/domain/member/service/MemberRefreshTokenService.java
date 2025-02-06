package com.example.groutine.domain.member.service;

import com.example.groutine.domain.member.entity.Member;
import com.example.groutine.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberRefreshTokenService {
    private final MemberRepository memberRepository;

    @Transactional
    public void saveRefreshToken(String refreshToken, Member member) {
        member.setRefreshToken(refreshToken);
    }

    @Transactional
    public void deleteRefreshToken(Member member) {
        member.setRefreshToken(null);
    }
}
