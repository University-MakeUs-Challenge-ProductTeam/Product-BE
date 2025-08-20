package umc.product.domain.event.entity.event;

import jakarta.persistence.*;
import lombok.*;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 행사 제목

    private String content; // 행사 내용

    private EventType eventType; // 행사 타입(중앙/지부/학교)

    private LocalDate eventDate;  // 행사 일자

    private LocalTime eventTime;  // 행사 소요 시간

    private String location;  // 행사 장소 (지도 검색 결과)

    private Integer maxParticipants;  // 최대 인원 (선택)

    @OneToMany
    @JoinColumn(name = "allowed_semester_id")
    private List<Semester> allowedSemesterList = new ArrayList<>();  // 행사 확인 가능 기수

    @ElementCollection
    @CollectionTable(name = "event_allowed_parts", joinColumns = @JoinColumn(name = "registration_settings_id"))
    @Enumerated(EnumType.STRING)
    private List<Part> allowedPartList = new ArrayList<>();  // 행사 확인 가능 파트

    @ElementCollection
    @CollectionTable(name = "event_allowed_roles", joinColumns = @JoinColumn(name = "registration_settings_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> allowedRoleList = new ArrayList<>();  // 행사 확인 가능 직책

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester; // 해당 행사가 속한 학기

    // 작성자의 정보로 참여 가능 소속 판별 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer; // 작성자

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventParticipation> participationEventList = new ArrayList<>(); // 이벤트를 참여 멤버

    public void changeImages(List<EventImage> eventImages) {
        this.images.clear(); // 기존 이미지 제거
        this.images.addAll(eventImages);
        for (EventImage image : eventImages) {
            image.setEvent(this); // 연관관계 주입
        }
    }

    public void updateInfo(String title, String content, EventType eventType, Semester semester,
                           LocalDate startDate, LocalTime endDate, String location, Integer maxParticipants,
                           List<Semester> allowedSemesterList, List<Part> allowedPartList, List<Role> allowedRoleList) {
        this.title = title;
        this.content = content;
        this.eventType = eventType;
        this.semester = semester;
        this.eventDate = startDate;
        this.eventTime = endDate;
        this.location = location;
        this.maxParticipants = maxParticipants;

        if (allowedSemesterList != null) {
            this.allowedSemesterList.addAll(allowedSemesterList);
        }

        this.allowedPartList.clear();
        if (allowedPartList != null) {
            this.allowedPartList.addAll(allowedPartList);
        }

        this.allowedRoleList.clear();
        if (allowedRoleList != null) {
            this.allowedRoleList.addAll(allowedRoleList);
        }
    }

    public String getThumbnail() {
        return this.images.stream()
                .findFirst()
                .map(EventImage::getUrl)
                .orElse(null);
    }
}
