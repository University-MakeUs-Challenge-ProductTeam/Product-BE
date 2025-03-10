package umc.product.domain.study.service.admin;

import umc.product.domain.member.entity.Member;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface AdminStudyMemberQueryService {

    List<Member> getMemberList(Study study);
    List<StudyMember> getRemovedStudyMemberList(Study study, List<AdminStudyMemberRequest> memberRequestList);
}
