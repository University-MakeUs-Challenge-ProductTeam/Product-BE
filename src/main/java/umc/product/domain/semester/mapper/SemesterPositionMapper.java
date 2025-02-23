package umc.product.domain.semester.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SemesterPositionMapper {
    public List<SemesterPosition> toSemesterPosition(Member member, Semester semester, List<MemberCodePropertiesResponse> adminCodePropertiesList){
        return adminCodePropertiesList.stream()
                .map(adminCodeProperties -> {
                    return SemesterPosition.builder()
                            .member(member)
                            .semester(semester)
                            .position(adminCodeProperties.getPosition())
                            .build();
                }).collect(Collectors.toList());
    }

}
