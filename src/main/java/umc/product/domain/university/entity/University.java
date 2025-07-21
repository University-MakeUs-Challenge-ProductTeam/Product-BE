package umc.product.domain.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.member.entity.Member;
import umc.product.domain.project.entity.ProjectUniversity;
import umc.product.domain.study.entity.StudyUniversity;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null AND is_active = true")
public class University extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectUniversity> projectUniversityList = new ArrayList<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyUniversity> studyUniversityList = new ArrayList<>();

    // branchUniversity 연관관계
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BranchUniversity> branchUniversityList = new ArrayList<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> memberList = new ArrayList<>();
}
