package umc.product.domain.member.dto.request.member.auth;

import umc.product.domain.member.entity.enums.LoginType;

import java.util.List;
public record MemberSignUpRequest (
        Long memberId,
        String name,
        String nickName,
        String email,
        LoginType loginType,
        String clientId,
        List<MemberSignUpSemesterPartRequest> semesterList
){

}
