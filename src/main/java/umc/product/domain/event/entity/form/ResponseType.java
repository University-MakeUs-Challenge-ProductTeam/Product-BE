package umc.product.domain.event.entity.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {
    TEXT("글 형식으로 입력"),
    FILE_UPLOAD("파일 업로드")
    ;

    private final String description;
}