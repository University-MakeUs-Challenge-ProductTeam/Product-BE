package umc.product.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.member.converter.RoleConverter;
import umc.product.domain.member.dto.request.admin.AdminProfileModifyRequest;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;
import umc.product.domain.member.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDate;
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

    @Setter
    @Convert(converter = RoleConverter.class)
    @Column(nullable = false)
    private Role role;

    private String name;

    private String nickName;

    private String email;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Setter
    private String avatarUrl;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Setter
    private List<SemesterPart> memberSemesterPart = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Setter
    private List<SemesterPosition> memberSemesterPosition = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Setter
    private List<MemberOut> memberOutList = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    public void changeRole(Role role) {
        this.role = role;
    }

    public void addSemesterPosition(List<SemesterPosition> semesterPositionList){
        this.memberSemesterPosition.addAll(semesterPositionList);
    }

    public void addSemesterPart(List<SemesterPart> semesterPartList) {
        this.memberSemesterPart.addAll(semesterPartList);
    }

    public void addMemberOut(MemberOut memberOut) {
        this.memberOutList.add(memberOut);
    }

    public void updateProfile(MemberSignUpRequest request) {
        this.name = request.getName();
        this.nickName = request.getNikeName();
        this.email = request.getEmail();
        this.loginType =request.getLoginType();
        this.clientId = request.getClientId();
    }

    public void modifyProfile(AdminProfileModifyRequest request, University university) {
        this.name = request.getName();
        this.nickName = request.getNickName();
        if(!this.university.equals(university)) this.university = university;
        this.status = request.getStatus();
    }

}