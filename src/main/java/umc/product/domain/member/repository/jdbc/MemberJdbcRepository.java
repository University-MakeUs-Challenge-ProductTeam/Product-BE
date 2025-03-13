package umc.product.domain.member.repository.jdbc;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Optional;

public interface MemberJdbcRepository {
    List<Member> saveRegisterNewMemberList(
            List<Member> memberList,
            List<SemesterPart> semesterPartList,
            List<SemesterPosition> semesterPositionList);

    void saveRegisterExistMemberList(
            List<SemesterPart> semesterPartList,
            List<SemesterPosition> semesterPositionList);
}
