package umc.product.domain.event.controller.member;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일반 사용자 용 행사 API", description = "행사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventReviewController {
}
