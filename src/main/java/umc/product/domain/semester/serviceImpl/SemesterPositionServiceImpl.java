package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.mapper.SemesterPositionMapper;
import umc.product.domain.semester.service.SemesterPositionService;

import java.util.List;

@Service
@AllArgsConstructor
public class SemesterPositionServiceImpl implements SemesterPositionService {
    private final SemesterPositionMapper semesterPositionMapper;

    @Override
    public List<SemesterPosition> toSemesterPosition(Member member, Semester semester, List<MemberCodePropertiesResponse> adminCodePropertiesList) {
        return semesterPositionMapper.toSemesterPosition(member,semester,adminCodePropertiesList);
    }
}
