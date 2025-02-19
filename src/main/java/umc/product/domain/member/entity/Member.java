package umc.product.domain.member.entity;

import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.member.converter.RoleConverter;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.university.entity.University;
import umc.product.global.common.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    // todo: 엔티티 추가되면 매핑 추가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role;

    private String name;

    private String nikeName;

    private String email;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String avatarUrl;

    private String birth;

    private Gender gender;

    private String clientId;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private MemberLoginInfo memberLoginInfo;

    @OneToMany(mappedBy = "participationMember", cascade = CascadeType.ALL)
    private List<ParticipationEvent> participationEventList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberProject> memberProjects = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    public void changeRole(Role role) {
        this.role = role;
    }

}