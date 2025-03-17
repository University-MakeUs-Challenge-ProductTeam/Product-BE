package umc.product.domain.semester.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPositionListRequest;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SemesterPositionMapper {
    public SemesterPosition toSemesterPosition(
            Member member,
            Semester semester,
            AdminInsertSemesterPositionListRequest.AdminInsertSemesterPositionRequest request
    ){
        if(request.centralPosition() == null && request.universityPosition() == null) {
            return SemesterPosition.builder()
                    .member(member)
                    .universityPosition("챌린저")
                    .centralPosition(null)
                    .semester(semester)
                    .build();
        }
        return SemesterPosition.builder()
                .member(member)
                .universityPosition(request.universityPosition())
                .centralPosition(request.centralPosition())
                .semester(semester)
                .build();
    }

    public List<SemesterPosition> toSemesterPosition(
            AdminRegisterListRequest request,
            List<Member> memberList,
            Semester recentSemester
    ) {
        Map<String, AdminRegisterListRequest.AdminRegisterMemberRequest> requestMap = request.registerMemberList().stream()
                .collect(Collectors.toMap(
                        ar -> ar.name() + "|"+  ar.nickName() + "|"+ ar.universityName() ,
                        ar -> ar
                ));

        return IntStream.range(0, memberList.size())
                .mapToObj(i -> {
                    AdminRegisterListRequest.AdminRegisterMemberRequest ar = requestMap.get(
                            memberList.get(i).getName() + "|" +
                                    memberList.get(i).getNickName() + "|" +
                                    memberList.get(i).getUniversity().getName()
                    );
                    if(ar.centralPosition() == null && ar.universityPosition() == null) {
                        return SemesterPosition.builder()
                                .member(memberList.get(i))
                                .semester(recentSemester)
                                .universityPosition("챌린저")
                                .centralPosition(null)
                                .build();
                    }else {
                        return SemesterPosition.builder()
                                .member(memberList.get(i))
                                .semester(recentSemester)
                                .universityPosition(ar.universityPosition())
                                .centralPosition(ar.centralPosition())
                                .build();
                    }
                })
                .collect(Collectors.toList());
    }
}
