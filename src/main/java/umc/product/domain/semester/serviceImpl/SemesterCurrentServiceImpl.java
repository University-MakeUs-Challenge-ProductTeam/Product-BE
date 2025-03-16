package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.repository.SemesterCurrentRepository;
import umc.product.domain.semester.service.SemesterCurrentService;
import umc.product.domain.semester.status.SemesterErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SemesterCurrentServiceImpl implements SemesterCurrentService {

    private final SemesterCurrentRepository semesterCurrentRepository;

    @Override
    public Semester getCurrentSemester() {
        return semesterCurrentRepository.findCurrentSemester()
                .orElseThrow(() -> new RestApiException(SemesterErrorStatus.SEMESTER_CURRENT_NOT_FOUND));
    }
}
