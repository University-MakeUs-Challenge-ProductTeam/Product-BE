package umc.product.domain.member.dto.request.admin.auth;

public record AdminLoginRequest(
        String clientId,
        String password
) {

}

