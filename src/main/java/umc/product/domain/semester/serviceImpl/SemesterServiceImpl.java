package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.member.MemberSignUpSemesterRequest;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.repository.SemesterCustomRepository;
import umc.product.domain.semester.service.SemesterService;

import java.util.List;

@Service
@AllArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterCustomRepository semesterCustomRepository;

    private final SemesterPartMapper semesterPartMapper;

    @Override
    public List<Semester> findSemesters(List<MemberSignUpSemesterRequest> semesterList) {
        return semesterCustomRepository.findSemesters(semesterList);
    }
}
