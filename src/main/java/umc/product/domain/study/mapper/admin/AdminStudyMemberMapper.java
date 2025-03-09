package umc.product.domain.study.mapper.admin;

import org.springframework.stereotype.Component;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.enums.StudyRole;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AdminStudyMemberMapper {

    public List<StudyMember> getStudyMemberList(Study study, List<AdminStudyMemberRequest> memberRequestList, List<SemesterPart> semesterPartList) {

        return IntStream.range(0, memberRequestList.size())
                .mapToObj(i -> {
                    AdminStudyMemberRequest request = memberRequestList.get(i);
                    SemesterPart semesterPart = semesterPartList.get(i);

                    return StudyMember.builder()
                            .semesterPart(semesterPart)
                            .study(study)
                            .studyRole(StudyRole.valueOf(request.getStudyRole().toUpperCase()))
                            .build();
                })
                .collect(Collectors.toList());
    }
}
