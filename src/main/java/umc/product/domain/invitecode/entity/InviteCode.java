package umc.product.domain.invitecode.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.joda.time.DateTime;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class InviteCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)  // 기본적으로 FetchType.LAZY 추천
    @CollectionTable(name = "invite_code_roles", joinColumns = @JoinColumn(name = "invite_code_id"))
    @Enumerated(EnumType.STRING)
    List<Role> roles = new ArrayList<>();

    private String code;

    private InviteCodeType type;

    private DateTime expireDate;
}
