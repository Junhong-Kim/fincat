package com.wagoowagoo.fincat.api.notice.controller;

import com.wagoowagoo.fincat.api.notice.dto.NoticeRequest;
import com.wagoowagoo.fincat.api.notice.dto.NoticeResponse;
import com.wagoowagoo.fincat.api.notice.entity.Notice;
import com.wagoowagoo.fincat.api.notice.service.NoticeService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public BaseResponse createNotice(HttpServletRequest request, @Valid @RequestBody NoticeRequest.CreateNotice dto) {
        Notice notice = noticeService.createNotice(request, dto);

        NoticeResponse.CreateNotice response = new NoticeResponse.CreateNotice(notice.getNoticeId());
        return new SuccessResponse<>(response);
    }
}
