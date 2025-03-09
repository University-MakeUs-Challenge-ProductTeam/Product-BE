package umc.product.domain.semester.repository;


import umc.product.domain.semester.entity.SemesterPart;

import java.util.List;

public interface SemesterPartRepository {
    List<SemesterPart> findAll();
    int count();
}
