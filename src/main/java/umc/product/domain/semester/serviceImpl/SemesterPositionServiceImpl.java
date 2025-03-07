package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPositionRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.mapper.SemesterPositionMapper;
import umc.product.domain.semester.service.SemesterPositionService;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.dto.excel.ExcelMember;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static umc.product.domain.semester.status.SemesterErrorStatus.EMPTY_SEMESTER;

@Service
@AllArgsConstructor
public class SemesterPositionServiceImpl implements SemesterPositionService {
    private final SemesterPositionMapper semesterPositionMapper;

    @Override
    public List<SemesterPosition> toSemesterPosition(AdminRegisterRequest request, List<Member> memberList, Semester recentSemester) {
        return semesterPositionMapper.toSemesterPosition(request, memberList, recentSemester);
    }

    @Override
    public List<SemesterPosition> toSemesterPosition(Member targetMember, List<AdminInsertSemesterPositionRequest> positionList, Map<Long, Semester> semesterMap) {
        return positionList.stream()
                .map(position -> {
                    Semester semester = Optional.ofNullable(semesterMap.get(position.semesterId()))
                            .orElseThrow(() -> new RestApiException(EMPTY_SEMESTER));

                    return semesterPositionMapper.toSemesterPosition(targetMember, semester ,position);
                }).collect(Collectors.toList());
    }

}
