package umc.product.domain.roadmap.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.product.domain.member.entity.enums.Part;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Roadmap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Part part;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL)
    private List<RoadmapWeek> roadmapWeekList;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL)
    private List<RoadmapSemester> roadmapSemesterList;

    public void update(String title, Part part) {
        this.title = title;
        this.part = part;
    }

}
