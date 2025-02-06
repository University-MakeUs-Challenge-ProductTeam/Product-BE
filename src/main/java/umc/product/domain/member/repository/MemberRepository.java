package com.example.groutine.domain.member.repository;

import com.example.groutine.domain.member.entity.Member;
import com.example.groutine.domain.member.entity.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByClientIdAndLoginType(String clientId, LoginType loginType);
    Optional<Member> findByName(String name);
}

