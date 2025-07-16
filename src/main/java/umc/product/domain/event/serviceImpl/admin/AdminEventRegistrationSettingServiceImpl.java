package umc.product.domain.event.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.dto.request.event.EventRegistrationSettingsRequest;
import umc.product.domain.event.dto.request.event.EventRegistrationSettingsUpdateRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventRegistrationSettings;
import umc.product.domain.event.mapper.EventRegistrationSettingsMapper;
import umc.product.domain.event.repository.EventRegistrationSettingsRepository;
import umc.product.domain.event.service.admin.AdminEventRegistrationSettingService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.service.SemesterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventRegistrationSettingServiceImpl implements AdminEventRegistrationSettingService {

    EventRegistrationSettingsMapper eventRegistrationSettingsMapper;
    EventRegistrationSettingsRepository eventRegistrationSettingsRepository;
    SemesterService semesterService;

    /*
     * 행사 폼 설정 생성
     */
    @Override
    @Transactional
    public EventRegistrationSettings createEventRegistrationSettings(EventRegistrationSettingsRequest request, Event event){
        List<Semester> allowedSemesters = semesterService.getSemesters(request.getAllowedSemesterIds());

        return createAndSaveEventRegistrationSettings(request, event, allowedSemesters);
    }

    /*
     * 행사 폼 설정 수정
     */
    @Override
    @Transactional
    public  EventRegistrationSettings updateeEventRegistrationSettings(EventRegistrationSettingsUpdateRequest request, Event event){
        EventRegistrationSettings eventRegistrationSettings = event.getRegistrationSettings();
        List<Semester> allowedSemesters = semesterService.getSemesters(request.getAllowedSemesterIds());
        eventRegistrationSettings.updateInfo(request, allowedSemesters);

        return eventRegistrationSettings;
    }

    private EventRegistrationSettings createAndSaveEventRegistrationSettings(EventRegistrationSettingsRequest request, Event event, List<Semester> allowedSemesters){
        EventRegistrationSettings eventRegistrationSettings = eventRegistrationSettingsMapper.toEventRegistrationSettings(request, event, allowedSemesters);
        return eventRegistrationSettingsRepository.save(eventRegistrationSettings);
    }


}
