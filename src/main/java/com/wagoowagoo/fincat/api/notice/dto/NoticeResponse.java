package com.wagoowagoo.fincat.api.notice.dto;

import com.wagoowagoo.fincat.api.notice.entity.Notice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class NoticeResponse {

    private NoticeResponse() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateNotice {

        private final long noticeId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class GetNoticeList {

        private final int totalPages;
        private final long totalElements;
        private final List<Notice> noticeList;
    }
}
