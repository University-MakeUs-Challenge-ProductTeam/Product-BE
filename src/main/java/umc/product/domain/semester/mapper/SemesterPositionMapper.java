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
        return SemesterPosition.builder()
                .member(member)
                .position(request.centralPosition() != null ? request.centralPosition() : request.universityPosition())
                .semester(semester)
                .centralStatus(request.centralPosition() != null)
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
                .mapToObj(i -> {    //반드시 2개 생성됨(직책이 없으면 NUll을 넣어줌)
                    AdminRegisterListRequest.AdminRegisterMemberRequest ar = requestMap.get(
                            memberList.get(i).getName() + "|" +
                                    memberList.get(i).getNickName() + "|" +
                                    memberList.get(i).getUniversity().getName()
                    );
                        List<SemesterPosition> semesterPositionList = new ArrayList<>();
                        semesterPositionList.add(
                                SemesterPosition.builder()
                                        .member(memberList.get(i))
                                        .semester(recentSemester)
                                        .position(ar != null ? ar.centralPosition(): null)
                                        .centralStatus(true)
                                        .build()
                        );

                        semesterPositionList.add(
                                SemesterPosition.builder()
                                        .member(memberList.get(i))
                                        .semester(recentSemester)
                                        .position(ar != null ? ar.universityPosition(): null)
                                        .centralStatus(false)
                                        .build()
                        );
                        return semesterPositionList;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
