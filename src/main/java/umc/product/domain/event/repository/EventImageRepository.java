package umc.product.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.EventImage;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
}
