package umc.product.domain.noticeMember.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.noticeMember.service.NoticeMemberService;

@Tag(name = "공지멤버 API", description = "공지멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/noticeMembers")
public class NoticeMemberController {
    private final NoticeMemberService noticeMemberService;
}
