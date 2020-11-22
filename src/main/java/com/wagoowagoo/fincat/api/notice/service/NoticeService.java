package com.wagoowagoo.fincat.api.notice.service;

import com.wagoowagoo.fincat.api.notice.dto.NoticeRequest;
import com.wagoowagoo.fincat.api.notice.entity.Notice;
import com.wagoowagoo.fincat.api.notice.repository.NoticeRepository;
import com.wagoowagoo.fincat.common.ErrorCode;
import com.wagoowagoo.fincat.exception.ApiException;
import com.wagoowagoo.fincat.util.JwtUtil;
import com.wagoowagoo.fincat.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Notice createNotice(HttpServletRequest request, NoticeRequest.CreateNotice dto) {
        String accessToken = RequestUtil.getAccessToken(request);
        String username = JwtUtil.extractUsername(accessToken);

        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .createdBy(username)
                .updatedBy(username)
                .build();
        return noticeRepository.save(notice);
    }

    public Page<Notice> getNoticeList(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    public Notice getNotice(long noticeId) {
        return noticeRepository
                .findById(noticeId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_NOTICE));
    }

    @Transactional
    public void updateNotice(long noticeId, NoticeRequest.UpdateNotice dto) {
        Notice notice = getNotice(noticeId);
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
    }
}
