package umc.product.domain.semester.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartListRequest;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.mapper.SemesterPartMapper;
import umc.product.domain.semester.repository.SemesterPartJpaRepository;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.status.SemesterErrorStatus;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
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
    private final SemesterPartJpaRepository semesterPartJpaRepository;
    private final AdminMemberService adminMemberService;
    @Override
    public List<SemesterPart> toSemesterPart(
            Member targetMember,
            List<AdminInsertSemesterPartListRequest.AdminInsertSemesterPartRequest> partList,
            Map<Long, Semester> semesterMap
    ) {
        return partList.stream()
                .map(part -> {
                    Semester semester = Optional.ofNullable(semesterMap.get(part.semesterId()))
                            .orElseThrow(() -> new RestApiException(EMPTY_SEMESTER));

                    return semesterPartMapper.toSemesterPart(targetMember, semester ,part);
                }).collect(Collectors.toList());
    }

    @Override
    public List<SemesterPart> toSemesterPart(AdminRegisterListRequest request, List<Member> memberList, Semester recentSemester) {
        return IntStream.range(0, request.registerMemberList().size())
                .mapToObj(i -> {
                    AdminRegisterListRequest.AdminRegisterMemberRequest ar = request.registerMemberList().get(i);
                    return SemesterPart.builder()
                            .member(memberList.get(i))
                            .semester(recentSemester)
                            .part(ar.part())
                            .build();
                }).collect(Collectors.toList());
    }
 
  @Override
  public List<SemesterPart> getSemesterPartList(String part, Semester semester, List<Member> memberList) {
        List<SemesterPart> semesterPartList = semesterPartJpaRepository.findBySemesterAndPartAndMemberIn(semester, Part.valueOf(part.toUpperCase()), memberList);

        if (semesterPartList == null || semesterPartList.isEmpty()) {
            throw new RestApiException(SemesterErrorStatus.NOT_MATCH_PART_MEMBER);
        }
        return semesterPartList;
    }

    @Override
    public List<SemesterPart> getSemesterPartList(Study study, List<AdminStudyMemberRequest> newMemberRequestList, SemesterPart baseSemesterPart) {
        // 새로 추가된 멤버의 memberId와 baseSemesterPart에서 semester와 part를 통해 List<SemesterPart> 가져오기
        List<Member> memberList = adminMemberService.getMemberList(newMemberRequestList);
        return semesterPartJpaRepository.findBySemesterAndPartAndMemberIn(baseSemesterPart.getSemester(), baseSemesterPart.getPart(), memberList);
    }
}
