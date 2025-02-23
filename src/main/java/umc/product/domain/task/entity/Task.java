package umc.product.domain.task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.entity.ProjectTask;
import umc.product.domain.project.entity.enums.Phase;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Task extends BaseEntity { // 과제

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Phase phase;

    @Enumerated(EnumType.STRING)
    private Part part;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTask> projectTasks = new ArrayList<>();
}

