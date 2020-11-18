package com.wagoowagoo.fincat.api.notice.service;

import com.wagoowagoo.fincat.api.notice.dto.NoticeRequest;
import com.wagoowagoo.fincat.api.notice.entity.Notice;
import com.wagoowagoo.fincat.api.notice.repository.NoticeRepository;
import com.wagoowagoo.fincat.util.JwtUtil;
import com.wagoowagoo.fincat.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
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
}
