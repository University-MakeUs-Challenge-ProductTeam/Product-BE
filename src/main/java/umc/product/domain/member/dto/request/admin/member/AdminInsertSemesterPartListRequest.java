package umc.product.domain.member.dto.request.admin.member;

import umc.product.domain.member.entity.enums.Part;

import java.util.List;
import java.util.Objects;

public record AdminInsertSemesterPartListRequest(
        List<AdminInsertSemesterPartRequest> semesterPartList) {

    public record AdminInsertSemesterPartRequest(
            Long semesterId,
            Part part
    ) {

    }
}

