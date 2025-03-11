package umc.product.domain.study.service.admin;

import umc.product.domain.member.entity.Member;
import umc.product.domain.study.entity.Study;

import java.util.List;

public interface AdminStudyUniversityCommandService {

    void createStudyUniversity(Study study, List<Member> memberList);
}
