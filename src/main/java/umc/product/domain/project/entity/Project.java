package umc.product.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.product.domain.branch.entity.Branch;
import umc.product.domain.member.entity.MemberProject;
import umc.product.domain.member.entity.MemberProjectPart;
import umc.product.domain.project.entity.mapping.ProjectTask;
import umc.product.domain.project.entity.mapping.ProjectUniversity;
import umc.product.domain.project.enums.Prize;
import umc.product.domain.task.entity.Task;
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

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean publishStatus;

    private String publishLink;

    @Enumerated(EnumType.STRING)
    private Prize prize;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectUniversity> projectUniversityList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTask> projectTaskList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberProject> memberProjectList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectPart> projectPartList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}