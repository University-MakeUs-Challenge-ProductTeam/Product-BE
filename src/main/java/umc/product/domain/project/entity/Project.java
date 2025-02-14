package umc.product.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.product.domain.project.entity.mapping.ProjectTask;
import umc.product.domain.project.entity.mapping.ProjectUniversity;
import umc.product.domain.project.enums.Prize;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Project extends BaseEntity { // 프로젝트

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String slogan;

    @Column(length = 1000)
    private String imageUrl;

    @Column(length = 1000)
    private String logoUrl;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean publishStatus;

    private String publishLink;

    @Enumerated(EnumType.STRING)
    private Prize prize;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectUniversity> projectUniversities = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTask> projectTasks = new ArrayList<>();

    // Part 연관 관계 매핑 추가 예정

    // Branch 연관 관계 매핑 추가 예정

    // MemberProject 연관 관계 매핑 추가 예정
}