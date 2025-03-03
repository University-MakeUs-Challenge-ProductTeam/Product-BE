package umc.product.domain.member.service.admin;

import umc.product.domain.member.entity.Member;

import java.util.List;
import java.util.Map;

public interface AdminCodeService {
    void saveWebAdminCode(String universityName, String code);
    void saveAppCode(Map<String, Member> codeMap);
    Map<String, Member> createAppCode(List<Member> memberList);
    Map<String, Member> createIndividualAppCode(Member member);
    String createWebAdminCode();
    String verifyWebAdminCode(String code);
}
