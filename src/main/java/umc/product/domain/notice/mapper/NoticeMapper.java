package umc.product.domain.notice.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.entity.Notice;

@Component
public class NoticeMapper {

    public Notice toEntity(AdminNoticeRequest request, Member writer) {
        return Notice.builder()
                .title(request.title())
                .content(request.content())
                .target(request.target())
                .hashtags(String.join(",", request.hashtags())) // 해시태그 리스트를 , 로 구분된 문자열로 변환
                .noticeSemesters(request.noticeSemesters())
                .noticeParts(request.noticeParts())
                .noticeDate(request.noticeDate())
                .checkDeadline(request.checkDeadline())
                .writer(writer)
                .build();
    }
}
