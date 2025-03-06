package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchListResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.out.MemberOutIdResponse;
import umc.product.domain.member.dto.response.member.out.MemberOutResponse;
import umc.product.domain.member.dto.response.member.search.MemberSearchResponse;
import umc.product.domain.member.dto.response.member.search.MemberSemesterPositionResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.config.security.jwt.TokenInfo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberOutConverter {
    public MemberOutIdResponse toMemberOutIdResponse(Long outId, Member member) {
        return MemberOutIdResponse.builder()
                .memberId(member.getId())
                .outId(outId)
                .build();
    }
}
