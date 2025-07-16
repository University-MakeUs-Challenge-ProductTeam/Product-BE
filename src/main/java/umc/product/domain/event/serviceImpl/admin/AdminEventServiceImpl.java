package umc.product.domain.event.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.dto.request.event.EventRegistrationSettingsRequest;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;
import umc.product.domain.event.entity.event.EventRegistrationSettings;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.mapper.EventMapper;
import umc.product.domain.event.repository.EventRepository;
import umc.product.domain.event.service.EventService;
import umc.product.domain.event.service.admin.AdminEventFormService;
import umc.product.domain.event.service.admin.AdminEventImageService;
import umc.product.domain.event.service.admin.AdminEventRegistrationSettingService;
import umc.product.domain.event.service.admin.AdminEventService;
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
    private final AdminEventFormService adminEventFormService;
    private final AdminEventRegistrationSettingService adminEventRegistrationSettingService;
    private final SemesterService semesterService;
    private final EventService eventService;

    /*
     * 행사 등록
     */
    @Override
    @Transactional
    public Event createEvent(
            Member writer, List<MultipartFile> eventImages, EventRequest request
    ){
        Semester semester = semesterService.getSemester(request.getSemesterId());

        Event newEvent = createAndSaveEvent(request, semester, writer, eventImages);

        EventForm newEventForm = adminEventFormService.createForm(newEvent, request.getForm());
        newEvent.setEventForm(newEventForm);

        EventRegistrationSettings newEventRegistrationSettings = adminEventRegistrationSettingService.createEventRegistrationSettings(request.getRegistrationSettings(), newEvent);
        newEvent.setEventRegistrationSettings(newEventRegistrationSettings);

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

        // 수정 권한 유효성 검사(본인이 아닌 경우 수정 불가)
        EventParamValidator.validModify(event.getWriter().getId(), writer.getId());


        updateEventInfo(event, request, semester);
        adminEventFormService.updateForm(event, request.getForm());
        adminEventRegistrationSettingService.updateeEventRegistrationSettings(request.getRegistrationSettings(), event);
        adminEventImageService.updateEventImages(event, request.getExistingImages(), newImages);

        return event;
    }



    private Event createAndSaveEvent(EventRequest request, Semester semester, Member writer, List<MultipartFile> eventImages) {
        Event newEvent = eventMapper.toEvent(request,semester,writer);
        return eventRepository.save(newEvent);
    }

    private void updateEventInfo(Event event, EventUpdateRequest request, Semester semester) {
        event.updateInfo(
                request.getTitle(),
                request.getContent(),
                request.getEventType(),
                semester,
                request.getEventStartDate(),
                request.getEventEndDate(),
                request.getLocation(),
                request.getMaxParticipants()
        );
    }

}
