package umc.product.domain.study.service.member;

import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.study.entity.StudyMember;

public interface StudyMemberQueryService {

    StudyMember getStudyMember(Member member, Long studyId);
    StudyMember getStudyMemberFetch(Member member, Long studyId);
    StudyMember getStudyMember(Semester currentSemester, Member member);
}
