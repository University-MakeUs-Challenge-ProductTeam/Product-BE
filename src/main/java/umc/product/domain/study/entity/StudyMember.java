package umc.product.domain.study.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.entity.enums.StudyRole;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StudyRole studyRole;

    @OneToMany(mappedBy = "studyMember", cascade = CascadeType.ALL)
    private List<StudyAttendance> studyAttendanceList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_part_id", nullable = false)
    private SemesterPart semesterPart;

    // todo - CheckList 매핑
}
