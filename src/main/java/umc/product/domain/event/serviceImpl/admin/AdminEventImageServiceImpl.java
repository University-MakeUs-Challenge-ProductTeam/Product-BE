package umc.product.domain.event.serviceImpl.admin;

import jakarta.transaction.Transactional;
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
import java.util.stream.Collectors;

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

    /*
     * 삭제할 기존 이미지를 S3와 DB에서 삭제
     */
    @Override
    public void deleteExistingImages(List<EventImage> imagesToRemove) {
        for (EventImage image : imagesToRemove) {
            s3FileUtil.deleteFile(image.getUrl());
            eventImageRepository.delete(image);
        }
    }

    /*
     * 행사 이미지 수정
     */
    @Override
    @Transactional
    public void updateEventImages(Event event, List<String> existingImageUrls, List<MultipartFile> newImages) {
        List<EventImage> existingImages = eventImageRepository.findAllByEvent(event);
        List<EventImage> existingImagesToKeep = existingImages.stream()
                .filter(image -> existingImageUrls.contains(image.getUrl()))
                .collect(Collectors.toList());

        List<EventImage> existingImagesToRemove = existingImages.stream()
                .filter(image -> !existingImageUrls.contains(image.getUrl()))
                .toList();

        // 새로운 이미지 추가
        List<EventImage> newEventImages = (newImages != null)
                ? createAndSaveEventImage(event, newImages) : List.of();

        // 유지할 기존 이미지와 새로운 이미지 병합 후 Event에 할당
        existingImagesToKeep.addAll(newEventImages);
        event.changeImages(existingImagesToKeep);

        // 삭제할 기존 이미지 삭제
        deleteExistingImages(existingImagesToRemove);
    }
}
