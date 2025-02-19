package umc.product.domain.university.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "학교 API", description = "학교 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/universities")
public class UniversityController {
}
