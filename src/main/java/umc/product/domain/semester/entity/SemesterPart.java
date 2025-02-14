package umc.product.domain.semester.entity;

import jakarta.persistence.*;
import lombok.Getter;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
public class SemesterPart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    private Part part;

    // todo : branch 추가해서 연관관계 매핑하기
}
