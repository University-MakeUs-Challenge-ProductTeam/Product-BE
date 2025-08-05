package umc.product.domain.event.entity.event;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import umc.product.global.common.base.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "deleted_at is null")
public class EventImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
    }
}