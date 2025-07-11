package umc.product.domain.event.serviceImpl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;
import umc.product.domain.event.mapper.EventImageMapper;
import umc.product.domain.event.repository.EventImageRepository;
import umc.product.domain.event.service.admin.AdminEventImageService;
import umc.product.global.util.S3FileUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventImageServiceImpl implements AdminEventImageService {

    private final EventImageRepository eventImageRepository;
    private final S3FileUtil s3FileUtil;
    private final EventImageMapper eventImageMapper;

    /*
     * 이벤트 이미지 객체를 생성하고 DB에 저장
     */
    @Override
    public List<EventImage> createAndSaveEventImage(Event event, List<MultipartFile> eventImages) {
        return eventImages.stream()
                .map(eventImage -> s3FileUtil.uploadFile("event", eventImage))
                .map(eventUrl -> eventImageMapper.toEventImage(event, eventUrl))
                .map(eventImageRepository::save)
                .toList();
    }
}
