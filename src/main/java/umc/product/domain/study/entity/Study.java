package umc.product.domain.study.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.base.BaseEntity;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyMember> studyMemberList;

    // 스터디 이름 변경 메서드
    public void changeName(String studyName) {
        if (studyName == null || studyName.trim().isEmpty()) {
            throw new RestApiException(StudyErrorStatus.STUDY_NAME_EMPTY);
        }
        this.name = studyName;
    }
}
