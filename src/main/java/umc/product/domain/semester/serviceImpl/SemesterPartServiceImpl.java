package umc.product.domain.semester.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.AdminPostSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.AdminSemesterPartRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.repository.SemesterPartRepository;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static umc.product.domain.semester.status.SemesterErrorStatus.EMPTY_SEMESTER;

@Service
@AllArgsConstructor
public class SemesterPartServiceImpl implements SemesterPartService {
    private final SemesterPartMapper semesterPartMapper;
    @Override
    public List<SemesterPart> toSemesterPart(Member targetMember, List<AdminPostSemesterPartRequest> partList, Map<Long, Semester> semesterMap) {
        return partList.stream()
                .map(part -> {
                    Semester semester = Optional.ofNullable(semesterMap.get(part.getSemesterId()))
                            .orElseThrow(() -> new RestApiException(EMPTY_SEMESTER));

                    return semesterPartMapper.toSemesterPart(targetMember, semester ,part);
                }).collect(Collectors.toList());
    }

}
