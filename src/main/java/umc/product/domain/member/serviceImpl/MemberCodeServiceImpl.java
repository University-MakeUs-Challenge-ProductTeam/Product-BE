package umc.product.domain.member.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.MemberCodeRequest;
import umc.product.domain.member.dto.response.MemberCodeResponse;
import umc.product.domain.member.entity.MemberCode;
import umc.product.domain.member.mapper.MemberCodeMapper;
import umc.product.domain.member.repository.MemberCodeRepository;
import umc.product.domain.member.service.MemberCodeService;

@Service
@AllArgsConstructor
public class MemberCodeServiceImpl implements MemberCodeService {
    private final MemberCodeRepository memberCodeRepository;
    private MemberCodeMapper memberCodeMapper;
    @Override
    public MemberCodeResponse saveCode(MemberCodeRequest request, String code) {
        MemberCode memberCode = MemberCode.builder()
                .code(code)
                .university(request.getUniversity())
                .roles(request.getRoles())
                .build();

        memberCodeRepository.save(memberCode);
        return memberCodeMapper.toMemberCode(memberCode.getCode());
    }
}
