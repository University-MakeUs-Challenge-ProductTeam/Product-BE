package umc.product.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@RedisHash(value = "code", timeToLive = 60*60*24) // 1일
public class MemberCode {
    @Id
    @Indexed
    private String code;
    private String university;
    private List<Role> roles;
}

