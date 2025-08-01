package umc.product.domain.chat.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    TEXT("텍스트"),
    IMAGE("이미지"),
    FILE("파일");
    
    private final String description;
} 