package umc.product.domain.project.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.member.entity.MemberProject;
import umc.product.domain.member.entity.enums.Part;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProjectPart { // ENUM 타입을 List로 저장하기 위한 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_project_id", nullable = false)
    private MemberProject memberProject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Part part;
}