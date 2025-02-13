package umc.product.domain.member.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.MemberCodeRequest;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberCodeResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.MemberAdminService;
import umc.product.domain.member.service.MemberCodeService;

import java.util.Random;
@Service
@AllArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {
    private final MemberCodeService memberCodeService;

    @Override
    public MemberCodeResponse generateCode(MemberCodeRequest request) {
        return memberCodeService.saveCode(request, generateVerificationCode());
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 1000000000 + random.nextInt(900000000); // 10자리 숫자 생성
        return String.valueOf(code);
    }
}
