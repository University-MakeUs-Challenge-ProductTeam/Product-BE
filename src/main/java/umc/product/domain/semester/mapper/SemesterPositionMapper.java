package umc.product.domain.semester.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPositionRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterMemberRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.dto.excel.ExcelMember;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SemesterPositionMapper {
    public SemesterPosition toSemesterPosition(Member member, Semester semester, AdminInsertSemesterPositionRequest request){
        return SemesterPosition.builder()
                .member(member)
                .position(request.position())
                .semester(semester)
                .build();
    }

    public List<SemesterPosition> toSemesterPosition(AdminRegisterRequest request, List<Member> memberList, Semester recentSemester) {
        return IntStream.range(0, memberList.size())
                .mapToObj(i -> {
                    AdminRegisterMemberRequest registerMemberRequest = request.registerMemberList().get(i);
                    List<SemesterPosition> semesterPositions = new ArrayList<>();
                    semesterPositions.add(
                            SemesterPosition.builder()
                                    .member(memberList.get(i))
                                    .semester(recentSemester)
                                    .position(registerMemberRequest.centralPosition())
                                    .build()
                    );

                    semesterPositions.add(
                            SemesterPosition.builder()
                                    .member(memberList.get(i))
                                    .semester(recentSemester)
                                    .position(registerMemberRequest.universityPosition())
                                    .build()
                    );
                    return semesterPositions;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
