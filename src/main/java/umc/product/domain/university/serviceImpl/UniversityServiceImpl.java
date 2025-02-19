package umc.product.domain.university.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.university.repository.UniversityRepository;
import umc.product.domain.university.service.UniversityService;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
}
