package umc.product.domain.event.entity.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.dto.request.event.EventRegistrationSettingsUpdateRequest;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany
    @JoinColumn(name = "allowed_semester_id")
    private List<Semester> allowedSemesterList = new ArrayList<>();  // 참여 가능 기수

    @ElementCollection
    @CollectionTable(name = "event_allowed_parts", joinColumns = @JoinColumn(name = "registration_settings_id"))
    @Enumerated(EnumType.STRING)
    private List<Part> allowedPartList = new ArrayList<>();  // 참여 가능 파트

    @ElementCollection
    @CollectionTable(name = "event_allowed_roles", joinColumns = @JoinColumn(name = "registration_settings_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> allowedRoleList = new ArrayList<>();  // 참여 가능 역할

    @Builder
    public EventRegistrationSettings(Event event, LocalDateTime registrationStartDate, LocalDateTime registrationEndDate, LocalDateTime cancellationDeadline, List<Semester> allowedSemesterList, List<Part> allowedPartList, List<Role> allowedRoleList) {
        this.event = event;
        this.registrationStartDate = registrationStartDate;
        this.registrationEndDate = registrationEndDate;
        this.cancellationDeadline = cancellationDeadline;
        // 빌더 패턴을 사용할 때, null 방지를 위해 초기값을 보장
        this.allowedSemesterList = (allowedSemesterList != null) ? allowedSemesterList : new ArrayList<>();
        this.allowedPartList = (allowedPartList != null) ? allowedPartList : new ArrayList<>();
        this.allowedRoleList = (allowedRoleList != null) ? allowedRoleList : new ArrayList<>();
    }

    public void updateInfo(EventRegistrationSettingsUpdateRequest request, List<Semester> allowedSemesters) {
        this.registrationStartDate = request.getRegistrationStartDate();
        this.registrationEndDate = request.getRegistrationEndDate();
        this.cancellationDeadline = request.getCancellationDeadline();
        this.allowedPartList.addAll(request.getAllowedPartList());
        this.allowedSemesterList.clear();
        this.allowedSemesterList.addAll(allowedSemesters);
        this.allowedPartList.clear();
        this.allowedPartList.addAll(request.getAllowedPartList());
        this.allowedRoleList.clear();
        this.allowedRoleList.addAll(request.getAllowedRoleList());
    }

}
