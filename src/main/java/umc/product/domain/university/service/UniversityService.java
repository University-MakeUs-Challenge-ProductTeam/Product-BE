package umc.product.domain.university.service;

import umc.product.domain.university.entity.University;

public interface UniversityService {
    public University findOrCreateUniversity(String universityName);

    public University findUniversity(String universityName);
}
