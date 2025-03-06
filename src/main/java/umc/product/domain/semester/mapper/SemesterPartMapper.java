package umc.product.domain.semester.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartRequest;
import umc.product.domain.member.dto.request.member.auth.MemberSignUpSemesterPartRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SemesterPartMapper {
    public SemesterPart toSemesterPart(Member member, Part part, Semester semester){
        return SemesterPart.builder()
                .part(part)
                .semester(semester)
                .member(member)
                .build();
    }

    public SemesterPart toSemesterPart(Member member, Semester semester, AdminInsertSemesterPartRequest request){
        return SemesterPart.builder()
                .member(member)
                .part(request.part())
                .semester(semester)
                .build();
    }

    public List<SemesterPart> toSemesterPart(List<Semester> semesterList, List<MemberSignUpSemesterPartRequest> commonSignUpSemesterList, Member member) {
        return IntStream.range(0, semesterList.size())
                .mapToObj(i -> SemesterPart.builder()
                        .member(member)
                        .semester(semesterList.get(i))
                        .part(commonSignUpSemesterList.get(i).part())
                        .build())
                .collect(Collectors.toList());
    }
}
