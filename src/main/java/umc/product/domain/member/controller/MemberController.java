package umc.product.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.domain.member.service.MemberService;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "멤버 API", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
}
