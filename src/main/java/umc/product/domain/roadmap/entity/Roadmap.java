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
    private int week;

    @Enumerated(EnumType.STRING)
    private Part part;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL)
    private List<RoadmapTitle> roadmapTitleList;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL)
    private List<RoadmapSemester> roadmapSemesterList;
}
