package umc.product.domain.event.service.admin.event;

import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;

import java.util.List;

public interface AdminEventImageService {
    List<EventImage> createAndSaveEventImage(Event event, List<MultipartFile> eventImages);
    void deleteExistingImages(List<EventImage> imagesToRemove);
    void updateEventImages(Event event, List<String> existingImageUrls, List<MultipartFile> newImages);
}