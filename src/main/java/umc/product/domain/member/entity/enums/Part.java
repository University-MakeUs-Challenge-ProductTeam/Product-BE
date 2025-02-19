package umc.product.domain.member.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Part {
    ANDROID("Android"),
    IOS("IOS"),
    SPRING("Spring"),
    NODE("Node.js"),
    DESIGN("Design"),
    WEB("Web"),
    PLAN("Plan");
    private final String name;
}
