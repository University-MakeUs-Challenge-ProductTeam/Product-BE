package umc.product.domain.semester.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Semester extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    private List<SemesterPart> semesterParts;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    private List<RoadmapSemester> roadmapSemesterList;
}
