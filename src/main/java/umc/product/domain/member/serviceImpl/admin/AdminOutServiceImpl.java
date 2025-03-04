package umc.product.domain.member.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.member.mapper.MemberOutMapper;
import umc.product.domain.member.repository.MemberOutRepository;
import umc.product.domain.member.service.admin.AdminOutService;
import umc.product.global.common.exception.RestApiException;

import static umc.product.domain.member.status.MemberErrorStatus.NOT_FOUND_OUT;

@Service
@RequiredArgsConstructor
public class AdminOutServiceImpl implements AdminOutService {
    private final MemberOutRepository memberOutRepository;

    private final MemberOutMapper memberOutMapper;

    @Transactional
    @Override
    public void postMemberOut(Member member, OutReason outReason) {
        MemberOut memberOut = memberOutMapper.toMemberOut(member, outReason);
        member.addMemberOut(memberOut);
        if(member.getMemberOutList().size() >=3) member.setStatus(Status.OUT);
    }

    @Transactional
    @Override
    public void modifyMemberOut(Long outId, OutReason outReason) {
        MemberOut memberOut = memberOutRepository.findMemberOutById(outId)
                .orElseThrow(()-> new RestApiException(NOT_FOUND_OUT));

        memberOut.setOutReason(outReason);
    }
    @Transactional
    @Override
    public void deleteMemberOut(Member member, Long outId) {
        memberOutRepository.deleteMemberOutById(outId)
                .orElseThrow(()-> new RestApiException(NOT_FOUND_OUT));
        if(member.getMemberOutList().size() < 3) member.setStatus(Status.ACTIVE);
    }
}
