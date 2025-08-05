package umc.product.domain.event.entity.event;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo : 글자수 제한
    private String title; // 행사 제목

    // todo : 글자수 제한
    private String content; // 행사 내용

    // todo : 글자수 제한
    private EventType eventType; // 행사 타입(어디서 주최하는 이벤트인지)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester; // 해당 행사가 속한 학기

    private LocalDateTime eventStartDate;  // 행사 시작 일시

    private LocalDateTime eventEndDate;  // 행사 종료 일시

    private String location;  // 행사 장소 (지도 검색 결과)

    private Integer maxParticipants;  // 최대 인원 (선택)

    // 작성자의 정보로 참여 가능 소속 판별 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer; // 작성자

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventParticipation> participationEventList = new ArrayList<>(); // 이벤트를 참가한 사람들

    // 역할 책임 분리를 위해 EventForm을 Event에서 분리해서 일대일 관계로 설정
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    @JoinColumn(name = "event_form_id")
    private EventForm eventForm; // 해당 행사에 대한 신청 폼

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private EventRegistrationSettings registrationSettings;  // 신청 관련 설정

    @Builder
    public Event(String title, String content, EventType eventType, Semester semester, LocalDateTime eventStartDate, LocalDateTime eventEndDate, String location, Integer maxParticipants, Member writer, List<EventImage> images, List<EventParticipation> participationEventList, EventForm eventForm, EventRegistrationSettings registrationSettings) {
        this.title = title;
        this.content = content;
        this.eventType = eventType;
        this.semester = semester;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.writer = writer;
        this.eventForm = eventForm;
        this.registrationSettings = registrationSettings;

        // 널 방지
        this.images = (images != null) ? images : new ArrayList<>();
        this.participationEventList = (participationEventList != null) ? participationEventList : new ArrayList<>();

    }

    public void changeImages(List<EventImage> eventImages) {
        this.images.clear(); // 기존 이미지 제거
        this.images.addAll(eventImages);
        for (EventImage image : eventImages) {
            image.setEvent(this); // 연관관계 주입
        }
    }

    public void setEventForm(EventForm eventForm) {
        this.eventForm = eventForm;
    }

    public void setEventRegistrationSettings(EventRegistrationSettings eventRegistrationSettings) {
        this.registrationSettings = eventRegistrationSettings;
    }

    public void updateInfo(String title, String content, EventType eventType, Semester semester,
                           LocalDateTime startDate, LocalDateTime endDate, String location, Integer maxParticipants) {
        this.title = title;
        this.content = content;
        this.eventType = eventType;
        this.semester = semester;
        this.eventStartDate = startDate;
        this.eventEndDate = endDate;
        this.location = location;
        this.maxParticipants = maxParticipants;
    }

    public String getThumbnail() {
        return this.images.stream()
                .findFirst()
                .map(EventImage::getUrl)
                .orElse(null);
    }
}
