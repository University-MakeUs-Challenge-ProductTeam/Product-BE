package umc.product.domain.event.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;
import umc.product.domain.event.entity.event.EventRegistrationSettings;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.mapper.EventMapper;
import umc.product.domain.event.repository.EventRepository;
import umc.product.domain.event.service.admin.AdminEventFormService;
import umc.product.domain.event.service.admin.AdminEventImageService;
import umc.product.domain.event.service.admin.AdminEventRegistrationSettingService;
import umc.product.domain.event.service.admin.AdminEventService;
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

    private Event createAndSaveEvent(EventRequest request, Semester semester, Member writer, List<MultipartFile> eventImages) {
        Event newEvent = eventMapper.toEvent(request,semester,writer);
        return eventRepository.save(newEvent);
    }
}
