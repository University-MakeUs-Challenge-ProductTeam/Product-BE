package umc.product.domain.member.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.code.MemberChallengerCodeRequest;
import umc.product.domain.member.dto.request.code.MemberAdminCodeRequest;
import umc.product.domain.member.dto.response.code.MemberCodeResponse;
import umc.product.domain.member.dto.response.code.MemberCodeRoleResponse;
import umc.product.domain.member.entity.MemberCode;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberCodeMapper;
import umc.product.domain.member.repository.MemberCodeRepository;
import umc.product.domain.member.service.MemberCodeService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static umc.product.global.common.exception.code.status.CodeErrorStatus.NOT_VAILD_CODE;

@Service
@AllArgsConstructor
public class MemberCodeServiceImpl implements MemberCodeService {
    private final MemberCodeRepository memberCodeRepository;
    private MemberCodeMapper memberCodeMapper;
    @Override
    public MemberCodeResponse saveAdminCode(MemberAdminCodeRequest request, String code) {
        MemberCode memberCode = MemberCode.builder()
                .code(code)
                .university(request.getUniversity())
                .roles(request.getRoles())  //권한 원하는대로 부여 가능, 가입시에 자동으로 넣을거임
                .build();

        memberCodeRepository.save(memberCode);
        return memberCodeMapper.toMemberCode(memberCode.getCode());
    }

    @Override
    public MemberCodeResponse saveChallengerCode(MemberChallengerCodeRequest request, String code) {
        MemberCode memberCode = MemberCode.builder()
                .code(code)
                .university(request.getUniversity())
                .roles(List.of(Role.CHALLENGER))
                .build();

        memberCodeRepository.save(memberCode);
        return memberCodeMapper.toMemberCode(memberCode.getCode());
    }

    @Override
    public String createAdminCode() {
        Random random = new Random();
        int code = 1000000000 + random.nextInt(900000000); // 10자리 숫자 생성
        return String.valueOf(code);
    }

    @Override
    public String createChallengerCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5).toUpperCase();
    }

    @Override
    public MemberCodeRoleResponse verifyMemberCode(String code) {
        MemberCode memberCode = memberCodeRepository.findById(code)
                .orElseThrow(()-> new RestApiException(NOT_VAILD_CODE));

        return memberCodeMapper.toMemberCodeRole(memberCode.getRoles());
    }
}
