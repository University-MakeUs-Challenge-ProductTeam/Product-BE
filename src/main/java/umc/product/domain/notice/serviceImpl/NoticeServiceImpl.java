package umc.product.domain.notice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.notice.repository.NoticeRepository;
import umc.product.domain.notice.service.NoticeService;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private NoticeRepository noticeRepository;
}
