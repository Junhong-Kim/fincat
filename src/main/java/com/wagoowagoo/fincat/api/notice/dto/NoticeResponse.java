package com.wagoowagoo.fincat.api.notice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class NoticeResponse {

    private NoticeResponse() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateNotice {

        private final long noticeId;
    }
}
