package umc.product.domain.member.dto.request.member;

import lombok.Getter;
import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;

import java.util.List;

@Getter
public class MemberSignUpRequest {
    private String name;
    private String nikeName;
    private String email;
    private LoginType loginType;
    private String birth;
    private String university;
    private Gender gender;
    private String clientId;
    private List<MemberSignUpSemesterRequest> semesterList;
    private List<MemberCodePropertiesResponse> memberCodePropertiesList;
}
