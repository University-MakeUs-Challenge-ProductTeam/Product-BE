package umc.product.domain.event.service.admin;

import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;

import java.util.List;

public interface AdminEventService {
    Event createEvent(Member writer, List<MultipartFile> eventImages, EventRequest eventRequest);
}
