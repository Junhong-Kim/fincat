package com.wagoowagoo.fincat.api.notice.controller;

import com.wagoowagoo.fincat.api.notice.dto.NoticeRequest;
import com.wagoowagoo.fincat.api.notice.dto.NoticeResponse;
import com.wagoowagoo.fincat.api.notice.entity.Notice;
import com.wagoowagoo.fincat.api.notice.service.NoticeService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public BaseResponse getNoticeList(final Pageable pageable) {
        Page<Notice> noticeList = noticeService.getNoticeList(pageable);

        NoticeResponse.GetNoticeList response = new NoticeResponse.GetNoticeList(
                noticeList.getTotalPages(),
                noticeList.getTotalElements(),
                noticeList.getContent()
        );
        return new SuccessResponse<>(response);
    }

    @GetMapping("/{noticeId}")
    public BaseResponse getNotice(@PathVariable("noticeId") final long noticeId) {
        Notice notice = noticeService.getNotice(noticeId);

        NoticeResponse.GetNotice response = new NoticeResponse.GetNotice(notice);
        return new SuccessResponse<>(response);
    }

    @PutMapping("/{noticeId}")
    public BaseResponse updateNotice(@PathVariable("noticeId") final long noticeId,
                                     @RequestBody NoticeRequest.UpdateNotice dto) {
        noticeService.updateNotice(noticeId, dto);

        return new SuccessResponse<>();
    }

    @DeleteMapping("/{noticeId}")
    public BaseResponse deleteNotice(HttpServletRequest request,
                                     @PathVariable("noticeId") final long noticeId) {
        noticeService.deleteNotice(request, noticeId);

        return new SuccessResponse<>();
    }
}
