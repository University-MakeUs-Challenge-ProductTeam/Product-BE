package umc.product.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.index.Indexed;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.Map;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberOut extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Setter
    @Enumerated(EnumType.STRING)
    private OutReason outReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}

