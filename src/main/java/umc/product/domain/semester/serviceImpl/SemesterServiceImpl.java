package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.member.MemberSignUpSemesterRequest;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.repository.SemesterCustomRepository;
import umc.product.domain.semester.service.SemesterService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

import static umc.product.domain.semester.status.SemesterErrorStatus.EMPTY_SEMESTER;

@Service
@AllArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterCustomRepository semesterCustomRepository;


    @Override
    public List<Semester> findSemesters(List<MemberSignUpSemesterRequest> semesterList) {
        List<Semester> findSemesterList = semesterCustomRepository.findSemesters(semesterList);
        if(findSemesterList.isEmpty()) throw new RestApiException(EMPTY_SEMESTER);
        return semesterCustomRepository.findSemesters(semesterList);
    }
}
