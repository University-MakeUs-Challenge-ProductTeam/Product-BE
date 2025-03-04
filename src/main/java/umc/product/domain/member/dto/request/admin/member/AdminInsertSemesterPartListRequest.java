package umc.product.domain.member.dto.request.admin.member;

import java.util.List;
import java.util.Objects;

public record AdminInsertSemesterPartListRequest(
        List<AdminInsertSemesterPartRequest> semesterPartList) {
    public AdminInsertSemesterPartListRequest(List<AdminInsertSemesterPartRequest> semesterPartList) {
        this.semesterPartList = List.copyOf(Objects.requireNonNullElse(semesterPartList, List.of()));
    }
}

