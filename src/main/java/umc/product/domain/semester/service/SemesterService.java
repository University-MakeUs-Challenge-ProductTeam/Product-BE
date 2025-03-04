package umc.product.domain.semester.service;

import umc.product.domain.member.dto.request.member.auth.MemberSignUpSemesterPartRequest;
import umc.product.domain.semester.entity.Semester;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface SemesterService {
    List<Semester> findSemesterListForSignup(List<MemberSignUpSemesterPartRequest> semesterList);
    <T> Map<Long, Semester> findSemesterListForModify(List<T> list, Function<T, Long> idExtractor);
    Semester findRecentSemester();
}
