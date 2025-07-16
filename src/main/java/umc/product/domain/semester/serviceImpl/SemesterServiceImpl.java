package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.member.auth.MemberSignUpRequest;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.repository.SemesterJpaRepository;
import umc.product.domain.semester.repository.SemesterRepository;
import umc.product.domain.semester.service.SemesterService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static umc.product.domain.semester.status.SemesterErrorStatus.EMPTY_SEMESTER;

@Service
@AllArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final SemesterJpaRepository semesterJpaRepository;


    @Override
    public List<Semester> findSemesterListForSignup(
            List<MemberSignUpRequest.MemberSignUpSemesterPartRequest> semesterList
    ) {
        List<Long> semesterIdList = semesterList.stream()
                .map(MemberSignUpRequest.MemberSignUpSemesterPartRequest::semesterId)
                .collect(Collectors.toList());
        List<Semester> findSemesterList = semesterRepository.findSemesterList(semesterIdList);
        if(findSemesterList.isEmpty()) throw new RestApiException(EMPTY_SEMESTER);
        return findSemesterList;
    }

    public <T> Map<Long, Semester> findSemesterListForModify(List<T> list, Function<T, Long> idExtractor) {
        List<Long> semesterIdList = list.stream()
                .map(idExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<Long, Semester> findSemesterList = semesterRepository.findSemesterMap(semesterIdList);
        if(findSemesterList.isEmpty()) throw new RestApiException(EMPTY_SEMESTER);
        return findSemesterList;
    }


    @Override
    public Semester findRecentSemester() {
        return semesterRepository.findRecentSemester();
    }

    @Override
    public Semester getSemester(Long semesterId) {
        return semesterJpaRepository.findById(semesterId)
                .orElseThrow(() -> new RestApiException(EMPTY_SEMESTER));
    }

    @Override
    public List<Semester> getSemesters(List<Long> semesterIds) {
        return semesterJpaRepository.findAllById(semesterIds);
    }
}
