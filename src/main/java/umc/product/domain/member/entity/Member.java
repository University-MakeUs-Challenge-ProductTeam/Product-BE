package umc.product.domain.member.entity;

import jakarta.validation.constraints.NotNull;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.University;
import umc.product.global.common.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private University university;

    private String name;

    private String nikeName;

    private String email;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String avatar_url;

    private String birth;

    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String password;

    public void changeRole(Role role) {
        this.role = role;
    }


}