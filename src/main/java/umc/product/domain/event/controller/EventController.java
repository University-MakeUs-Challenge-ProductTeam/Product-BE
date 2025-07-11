package umc.product.domain.event.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이벤트 API", description = "이벤트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
}
