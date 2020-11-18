package com.wagoowagoo.fincat.api.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class NoticeRequest {

    private NoticeRequest() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateNotice {

        @NotBlank
        private String title;

        @NotBlank
        private String contents;
    }
}
