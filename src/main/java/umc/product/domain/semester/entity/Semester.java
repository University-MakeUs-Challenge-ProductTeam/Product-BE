package umc.product.domain.semester.entity;

import jakarta.persistence.*;
import lombok.Getter;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
public class Semester extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    private List<SemesterPart> semesterParts;
}
