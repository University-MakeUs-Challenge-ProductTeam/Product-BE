package umc.product.domain.study.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.enums.Check;
import umc.product.domain.study.repository.admin.AdminStudyAttendanceRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudyAttendanceCommandServiceImpl implements AdminStudyAttendanceCommandService {

    private final AdminStudyAttendanceRepository adminStudyAttendanceRepository;

    @Override
    public void createStudyAttendance(List<StudyMember> studyMemberList) {

        List<StudyAttendance> attendanceList = studyMemberList.stream()
                .flatMap(studyMember -> IntStream.rangeClosed(0, 12) // 일단 널널하게 12주차까지 생성
                        .mapToObj(week -> StudyAttendance.builder()
                                .studyMember(studyMember)
                                .week(week)
                                .checkStatus(Check.UNSET) // 모든 주차의 초기 상태는 'UNSET'
                                .build()))
                .collect(Collectors.toList());

        adminStudyAttendanceRepository.saveAll(attendanceList);
    }
}
