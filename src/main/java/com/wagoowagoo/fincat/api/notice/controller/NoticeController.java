package com.wagoowagoo.fincat.api.notice.controller;

import com.wagoowagoo.fincat.api.notice.dto.NoticeRequest;
import com.wagoowagoo.fincat.api.notice.dto.NoticeResponse;
import com.wagoowagoo.fincat.api.notice.entity.Notice;
import com.wagoowagoo.fincat.api.notice.service.NoticeService;
import com.wagoowagoo.fincat.common.BaseResponse;
import com.wagoowagoo.fincat.common.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "공지사항 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @ApiOperation(value = "공지사항 생성")
    @PostMapping
    public BaseResponse createNotice(HttpServletRequest request, @Valid @RequestBody NoticeRequest.CreateNotice dto) {
        Notice notice = noticeService.createNotice(request, dto);

        NoticeResponse.CreateNotice response = new NoticeResponse.CreateNotice(notice.getNoticeId());
        return new SuccessResponse<>(response);
    }

    @ApiOperation(value = "공지사항 목록 조회")
    @GetMapping
    public BaseResponse getNoticeList(
            @ApiParam(value = "페이지", required = true) @RequestParam int page
            , @ApiParam(value = "크기", required = true) @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Notice> noticeList = noticeService.getNoticeList(pageRequest);

        NoticeResponse.GetNoticeList response = new NoticeResponse.GetNoticeList(
                noticeList.getTotalPages(),
                noticeList.getTotalElements(),
                noticeList.getContent()
        );
        return new SuccessResponse<>(response);
    }

    @ApiOperation(value = "공지사항 단건 조회")
    @GetMapping("/{noticeId}")
    public BaseResponse getNotice(@PathVariable("noticeId") final long noticeId) {
        Notice notice = noticeService.getNotice(noticeId);

        NoticeResponse.GetNotice response = new NoticeResponse.GetNotice(notice);
        return new SuccessResponse<>(response);
    }

    // FIXME: 관리자 전용 API
    @ApiOperation(value = "공지사항 수정")
    @PutMapping("/{noticeId}")
    public BaseResponse updateNotice(@PathVariable("noticeId") final long noticeId,
                                     @RequestBody NoticeRequest.UpdateNotice dto) {
        noticeService.updateNotice(noticeId, dto);

        return new SuccessResponse<>();
    }

    // FIXME: 관리자 전용 API
    @ApiOperation(value = "공지사항 삭제")
    @DeleteMapping("/{noticeId}")
    public BaseResponse deleteNotice(HttpServletRequest request,
                                     @PathVariable("noticeId") final long noticeId) {
        noticeService.deleteNotice(request, noticeId);

        return new SuccessResponse<>();
    }
}
