package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterMemberRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static umc.product.domain.semester.status.SemesterErrorStatus.EMPTY_SEMESTER;

@Service
@AllArgsConstructor
public class SemesterPartServiceImpl implements SemesterPartService {
    private final SemesterPartMapper semesterPartMapper;
    @Override
    public List<SemesterPart> toSemesterPart(Member targetMember, List<AdminInsertSemesterPartRequest> partList, Map<Long, Semester> semesterMap) {
        return partList.stream()
                .map(part -> {
                    Semester semester = Optional.ofNullable(semesterMap.get(part.semesterId()))
                            .orElseThrow(() -> new RestApiException(EMPTY_SEMESTER));

                    return semesterPartMapper.toSemesterPart(targetMember, semester ,part);
                }).collect(Collectors.toList());
    }

    @Override
    public List<SemesterPart> toSemesterPart(AdminRegisterRequest request, List<Member> memberList, Semester recentSemester) {
        return IntStream.range(0, request.registerMemberList().size())
                .mapToObj(i -> {
                    AdminRegisterMemberRequest ar = request.registerMemberList().get(i);
                    return SemesterPart.builder()
                            .member(memberList.get(i))
                            .semester(recentSemester)
                            .part(ar.part())
                            .build();
                }).collect(Collectors.toList());
    }

}
