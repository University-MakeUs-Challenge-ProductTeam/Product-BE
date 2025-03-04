package umc.product.domain.member.dto.request.admin.auth;

public record AdminSignUpRequest (
        String email,
        String universityName,
        String clientId,
        String password
){

}
