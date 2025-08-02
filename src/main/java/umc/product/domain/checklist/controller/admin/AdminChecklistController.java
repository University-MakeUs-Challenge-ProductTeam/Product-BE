package umc.product.domain.checklist.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.checklist.adviser.admin.AdminChecklistAdviser;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.global.common.base.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/checklists")
public class AdminChecklistController {
  private final AdminChecklistAdviser adminChecklistAdviser;

  @PostMapping
  @Operation(summary = "체크리스트 생성 API", description = "특정 기수의 특정 주차, 파트에 대해 체크리스트를 생성합니다.")
  @ApiResponse(responseCode = "200", description = "체크리스트 생성 성공")
  public BaseResponse<List<ChecklistCommonResponse>> createChecklist(@Valid @RequestBody AdminChecklistRequest request) {
    return BaseResponse.onSuccess(adminChecklistAdviser.createChecklist(request));
  }

}
