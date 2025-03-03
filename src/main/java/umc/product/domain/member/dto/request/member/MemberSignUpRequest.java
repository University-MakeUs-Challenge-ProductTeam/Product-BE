package umc.product.domain.member.dto.request.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import umc.product.domain.member.entity.enums.Gender;
import umc.product.domain.member.entity.enums.LoginType;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MemberSignUpRequest {
    private Long memberId;
    private String name;
    private String nikeName;
    private String email;
    private LoginType loginType;
    private String clientId;
    private List<MemberSignUpSemesterRequest> semesterList;
}
