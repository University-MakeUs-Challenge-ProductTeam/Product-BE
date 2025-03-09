package umc.product.domain.study.mapper.admin;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyUniversity;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminStudyUniversityMapper {

    public List<University> toUniversity(List<Member> memberList) {
        return memberList.stream()
                .map(Member::getUniversity)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<StudyUniversity> toStudyUniversityList(Study study, List<University> universityList) {
        return universityList.stream()
                .map(university -> StudyUniversity.builder()
                        .study(study)
                        .university(university)
                        .build())
                .collect(Collectors.toList());
    }
}
