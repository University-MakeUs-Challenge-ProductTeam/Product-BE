package umc.product.domain.semester.repository;

import umc.product.domain.semester.entity.Semester;

import java.util.List;
import java.util.Map;

public interface SemesterRepository {
    List<Semester> findSemesterList(List<Long> semesterIdList);
    Map<Long, Semester> findSemesterMap(List<Long> semesterIdList);
    Semester findRecentSemester();
}
