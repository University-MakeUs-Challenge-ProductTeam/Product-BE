package umc.product.domain.semester.repository;

import umc.product.domain.member.dto.request.member.MemberSignUpSemesterRequest;
import umc.product.domain.semester.entity.Semester;

import java.util.List;

public interface SemesterCustomRepository {
    public List<Semester> findSemesters(List<MemberSignUpSemesterRequest> semesterList);
}
