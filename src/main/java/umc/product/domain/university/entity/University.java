package umc.product.domain.university.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.global.common.base.BaseEntity;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
public class University extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isActive;
}
