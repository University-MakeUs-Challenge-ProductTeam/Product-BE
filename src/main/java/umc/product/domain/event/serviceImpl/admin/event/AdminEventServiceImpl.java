package umc.product.domain.event.serviceImpl.admin.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.dto.response.event.AdminEventSummaryResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.mapper.EventMapper;
import umc.product.domain.event.repository.jpa.event.EventImageRepository;
import umc.product.domain.event.repository.jpa.event.EventRepository;
import umc.product.domain.event.repository.jpa.participation.EventParticipationRepository;
import umc.product.domain.event.repository.querydsl.EventDslRepository;
import umc.product.domain.event.service.member.event.EventService;
import umc.product.domain.event.service.admin.event.AdminEventImageService;
import umc.product.domain.event.service.admin.event.AdminEventService;
import umc.product.domain.event.validator.EventParamValidator;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.service.SemesterService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final AdminEventImageService adminEventImageService;
    private final SemesterService semesterService;
    private final EventService eventService;
    private final EventImageRepository eventImageRepository;
    private final EventDslRepository eventDslRepository;
    private final EventConverter eventConverter;
    private final EventParticipationRepository eventParticipationRepository;

    /*
     * 행사 등록
     */
    @Override
    @Transactional
    public Event createEvent(
            Member writer, List<MultipartFile> eventImages, EventRequest request
    ){
        Semester semester = semesterService.getSemester(request.getSemesterId());

        List<Semester> allowedSemesters = semesterService.getSemesters(request.getAllowedSemesterIds());

        Event newEvent = createAndSaveEvent(request, semester, writer, allowedSemesters);

        //이미지 S3 저장
        if(eventImages !=null && !eventImages.isEmpty()){
            List<EventImage> newEventImages = adminEventImageService.createAndSaveEventImage(newEvent, eventImages);
            newEvent.changeImages(newEventImages);
        }

        return newEvent;
    }

    /*
     * 행사 수정
     */
    @Override
    @Transactional
    public Event updateEvent(Member writer, Long eventId, EventUpdateRequest request, List<MultipartFile> newImages){
        Event event = eventService.getEvent(eventId);
        Semester semester = semesterService.getSemester(request.getSemesterId());
        List<Semester> allowedSemesters = semesterService.getSemesters(request.getAllowedSemesterIds());

        // 수정 권한 유효성 검사(본인이 아닌 경우 수정 불가)
        EventParamValidator.validModify(event.getWriter().getId(), writer.getId());

        updateEventInfo(event, request, semester, allowedSemesters);
        adminEventImageService.updateEventImages(event, request.getExistingImages(), newImages);

        return event;
    }

    /*
     * 행사 삭제
     */
    @Override
    @Transactional
    public Event deleteEvent(Member writer, Long eventId){
        Event event = eventRepository.getEvent(eventId);

        // 수정 권한 유효성 검사(본인이 아닌 경우 삭제 불가)
        EventParamValidator.validModify(event.getWriter().getId(), writer.getId());

        //S3 이미지 삭제
        List<EventImage> images = eventImageRepository.findAllByEvent(event);
        adminEventImageService.deleteExistingImages(images);
        eventRepository.delete(event);

        return event;
    }

    /*
     * 운영진 행사 목록 조회
     */
    @Override
    @Transactional
    public Page<AdminEventSummaryResponse> inquiryEventsByFilter(
            Integer month, String semester, EventType eventType, int page, int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = eventDslRepository.findByFilter(pageable, month, semester, eventType);
        return events.map(event -> {
            int count = eventParticipationRepository.countByEvent(event);
            //todo 연관 공지 추가 하기.
            //int noticeCount = eventNoticeRepository.countByEvent(event);
            return eventConverter.toAdminEventSummaryResponse(event, count, 1); // 내부에서 DTO 변환용
        });
    }

    /*
     * 운영진 행사 상세 조회
     */
    @Override
    @Transactional
    public Event inquiryEventDetail(Long eventId){
        eventRepository.getEvent(eventId);

        return eventRepository.getEvent(eventId);
    }


    private Event createAndSaveEvent(EventRequest request, Semester semester, Member writer, List<Semester> allowedSemester) {
        Event newEvent = eventMapper.toEvent(request,semester,writer, allowedSemester);
        return eventRepository.save(newEvent);
    }

    private void updateEventInfo(Event event, EventUpdateRequest request, Semester semester, List<Semester> allowedSemester) {
        event.updateInfo(
                request.getTitle(),
                request.getContent(),
                request.getEventType(),
                semester,
                request.getEventDate(),
                request.getEventTime(),
                request.getLocation(),
                request.getMaxParticipants(),
                allowedSemester,
                request.getAllowedPartList(),
                request.getAllowedRoleList()
        );
    }

}
