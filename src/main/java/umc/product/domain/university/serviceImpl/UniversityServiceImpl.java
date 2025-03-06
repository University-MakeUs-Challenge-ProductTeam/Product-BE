package umc.product.domain.university.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.repository.UniversityRepository;
import umc.product.domain.university.service.UniversityService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

import static umc.product.domain.university.status.UniversityErrorStatus.EMPTY_UNIVERSITY;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;

    @Override
    public University findOrCreateUniversity(String universityName) {
        return universityRepository.findUniversityByName(universityName)
                .orElseGet(() -> {
                    University newUniversity = University.builder()
                            .name(universityName)
                            .isActive(true)
                            .build();
                    return universityRepository.save(newUniversity);  // 새로 생성된 대학을 저장하고 반환
                });
    }

    @Override
    public University findUniversity(String universityName) {
        return universityRepository.findUniversityByName(universityName)
                .orElseThrow(()-> new RestApiException(EMPTY_UNIVERSITY));
    }

    @Override
    public List<University> findUniversityList() {
        return universityRepository.findAll();
    }
}
