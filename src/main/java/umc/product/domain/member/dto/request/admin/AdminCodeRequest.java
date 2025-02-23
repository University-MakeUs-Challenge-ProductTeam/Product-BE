package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;

import java.util.List;

@Getter
public class AdminCodeRequest {
    private String university;
    private List<MemberCodePropertiesResponse> memberCodePropertiesList;
}
