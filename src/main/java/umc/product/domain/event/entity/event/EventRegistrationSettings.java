package umc.product.domain.event.entity.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventRegistrationSettings extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event; // 해당 신청 설정이 속한 행사

    private LocalDateTime registrationStartDate;  // 신청 시작 일시

    private LocalDateTime registrationEndDate;  // 신청 종료 일시

    private LocalDateTime cancellationDeadline;  // 취소 가능 기한

    @OneToMany(mappedBy = "eventRegistrationSettings", cascade = CascadeType.ALL)
    private List<Semester> allowedSemester;  // 참여 가능 기수

    @ElementCollection
    @CollectionTable(name = "event_allowed_parts", joinColumns = @JoinColumn(name = "registration_settings_id"))
    @Enumerated(EnumType.STRING)
    private List<Part> allowedParts;  // 참여 가능 파트

    @ElementCollection
    @CollectionTable(name = "event_allowed_roles", joinColumns = @JoinColumn(name = "registration_settings_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> allowedRoles;  // 참여 가능 역할

    @Builder
    public EventRegistrationSettings(Event event, LocalDateTime registrationStartDate, LocalDateTime registrationEndDate, LocalDateTime cancellationDeadline, List<Semester> allowedSemester, List<Part> allowedParts, List<Role> allowedRoles) {
        this.event = event;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.cancellationDeadline = cancellationDeadline;
        this.allowedSemester = allowedSemester;
        this.allowedParts = allowedParts;
        this.allowedRoles = allowedRoles;
    }
}
