package umc.product.domain.university.service;

import umc.product.domain.university.entity.University;

import java.util.List;

public interface UniversityService {
    public University findOrCreateUniversity(String universityName);

    public University findUniversity(String universityName);

    public List<University> findUniversityList();
}
