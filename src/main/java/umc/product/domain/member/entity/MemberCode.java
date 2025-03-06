package umc.product.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;
import java.util.Map;

@AllArgsConstructor
@Getter
@Builder
public class MemberCode {
    @Id
    @Indexed
    private String code;
    private Map<String, Object> properties;
}

