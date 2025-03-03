package umc.product.domain.semester.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.admin.AdminPostSemesterPositionRequest;
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
    public SemesterPosition toSemesterPosition(Member member, Semester semester, AdminPostSemesterPositionRequest request){
        return SemesterPosition.builder()
                .member(member)
                .position(request.getPosition())
                .semester(semester)
                .build();
    }

    public List<SemesterPosition> toSemesterPosition(List<Member> memberList, List<ExcelMember> excelMemberList, Semester recentSemester) {
        return IntStream.range(0, memberList.size())
                .mapToObj(i -> {
                    ExcelMember excelMember = excelMemberList.get(i);
                    List<SemesterPosition> semesterPositions = new ArrayList<>();
                        semesterPositions.add(
                                SemesterPosition.builder()
                                        .member(memberList.get(i))
                                        .semester(recentSemester)
                                        .position(excelMember.getCentralPosition())
                                        .build()
                        );

                        semesterPositions.add(
                                SemesterPosition.builder()
                                        .member(memberList.get(i))
                                        .semester(recentSemester)
                                        .position(excelMember.getUniversityPosition())
                                        .build()
                        );
                    return semesterPositions;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
