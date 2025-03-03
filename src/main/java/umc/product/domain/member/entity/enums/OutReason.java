package umc.product.domain.member.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutReason {
    NO_SHOW("행사 노쇼"),
    NOTICE_CHECK_NOT_PERFORM("공지 열람 체크 미수행"),
    STUDY_CHECK_NOT_PERFORM("스토디 체크 리스트 미수행"),
    PROJECT_CHECK_NOT_PERFORM("프로젝트 차수별 과제 체크 리스트 미수행"),
    PROJECT_ASSIGNMENT_NOT_PERFORM("프로젝트 차수별 과제 제출 미수행");

    private final String toKorean;
}
