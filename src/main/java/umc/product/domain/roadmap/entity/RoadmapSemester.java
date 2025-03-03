package umc.product.domain.roadmap.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadmapSemester extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @OneToMany(mappedBy = "roadmapSemester", cascade = CascadeType.ALL)
    private List<Checklist> checklistList;
}
