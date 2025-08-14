package umc.product.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import umc.product.domain.checklist.entity.enums.ChecklistCategory;
import umc.product.domain.checklist.entity.enums.ChecklistType;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Checklist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int week;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChecklistType checklistType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChecklistCategory checklistCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_semester_id", nullable = false)
    private RoadmapSemester roadmapSemester;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL)
    private List<ChecklistContent> checklistContentList;

    public void update(String title, int week, ChecklistType type, ChecklistCategory category) {
        this.title = title;
        this.week = week;
        this.checklistType = type;
        this.checklistCategory = category;
    }
}
