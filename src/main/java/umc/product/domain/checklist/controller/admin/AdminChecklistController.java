package umc.product.domain.checklist.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.checklist.adviser.admin.AdminChecklistAdviser;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistUpdateRequest;
import umc.product.domain.checklist.dto.response.admin.AdminChecklistResponse;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.member.entity.enums.Part;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "관리자용(웹) CHECKLIST API", description = "관리자용(웹) 체크리스트 관련 API")
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

  @PatchMapping("/roadmaps/{roadmapId}/weeks/{week}")
  @Operation(summary = "체크리스트 수정 API", description = "특정 주차의 체크리스트를 일괄 수정합니다.")
  public BaseResponse<List<ChecklistCommonResponse>> updateChecklists(
      @PathVariable Long roadmapId,
      @PathVariable int week,
      @Valid @RequestBody AdminChecklistUpdateRequest request
  ) {
    return BaseResponse.onSuccess(adminChecklistAdviser.updateChecklists(roadmapId, week, request));
  }

  @DeleteMapping("/{checklistId}")
  @Operation(summary = "체크리스트 삭제 API", description = "ID로 특정 체크리스트와 하위 항목들을 모두 삭제합니다.")
  @ApiResponse(responseCode = "200", description = "체크리스트 삭제 성공")
  public BaseResponse<ChecklistCommonResponse> deleteChecklist(@PathVariable Long checklistId) {
    Long deletedId = adminChecklistAdviser.deleteChecklist(checklistId);
    return BaseResponse.onSuccess(ChecklistCommonResponse.from(deletedId));
  }


}
