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

import java.util.List;
import java.util.stream.Collectors;

import static umc.product.domain.member.status.MemberErrorStatus.NOT_FOUND_OUT;

@Service
@RequiredArgsConstructor
public class AdminOutServiceImpl implements AdminOutService {
    private final MemberOutRepository memberOutRepository;

    private final MemberOutMapper memberOutMapper;

    @Transactional
    @Override
    public MemberOut postMemberOut(Member member, OutReason outReason) {
        MemberOut memberOut = memberOutMapper.toMemberOut(member, outReason);
        member.addMemberOut(memberOut);
        List<MemberOut> memberOutList = member.getMemberOutList().stream()
                .filter(memberOut1 -> memberOut1.getDeletedAt() == null)    //삭제 되지 않은 것만 추출
                .collect(Collectors.toList());
        if(memberOutList.size() >=3) member.setStatus(Status.OUT);
        return memberOut;
    }

    @Transactional
    @Override
    public void modifyMemberOut(Long outId, OutReason outReason, Member member) {
        MemberOut memberOut = memberOutRepository.findMemberOutById(outId)
                .orElseThrow(()-> new RestApiException(NOT_FOUND_OUT));

        if(!memberOut.getMember().getId().equals(member.getId())) throw new RestApiException(NOT_FOUND_OUT);

        memberOut.setOutReason(outReason);
    }
    @Transactional
    @Override
    public void deleteMemberOut(Member member, Long outId) {
        MemberOut memberOut = memberOutRepository.findMemberOutById(outId)
                .orElseThrow(()-> new RestApiException(NOT_FOUND_OUT));
        if(!memberOut.getMember().getId().equals(member.getId())) throw new RestApiException(NOT_FOUND_OUT);
        memberOut.delete();
        List<MemberOut> memberOutList = member.getMemberOutList().stream()
                .filter(memberOut1 -> memberOut1.getDeletedAt() == null)    //삭제 되지 않은 것만 추출
                .collect(Collectors.toList());
        if(memberOutList.size() < 3) member.setStatus(Status.ACTIVE);
    }
}
