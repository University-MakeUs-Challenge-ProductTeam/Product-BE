package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.repository.SemesterPartRepository;
import umc.product.domain.semester.service.SemesterPartService;

@Service
@AllArgsConstructor
public class SemesterPartServiceImpl implements SemesterPartService {
    private final SemesterPartMapper semesterPartMapper;

    @Override
    public SemesterPart saveSemesterPart(Member member, Part part, Semester semester) {
        return semesterPartMapper.toSemesterPart(member, part, semester);
    }
}
