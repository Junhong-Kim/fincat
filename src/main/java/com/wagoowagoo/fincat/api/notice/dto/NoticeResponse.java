package com.wagoowagoo.fincat.api.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wagoowagoo.fincat.api.notice.entity.Notice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class NoticeResponse {

    private NoticeResponse() {
        throw new IllegalStateException("DTO 클래스는 인스턴스 생성이 금지되어 있습니다.");
    }

    @Getter
    @RequiredArgsConstructor
    public static class CreateNotice implements Serializable {

        private static final long serialVersionUID = 95113416764112413L;

        private final long noticeId;
    }

    @Getter
    @RequiredArgsConstructor
    public static class GetNoticeList implements Serializable {

        private static final long serialVersionUID = 2155766692853816657L;

        private final int totalPages;
        private final long totalElements;
        private final List<Notice> noticeList;
    }

    @Getter
    public static class GetNotice implements Serializable {

        private static final long serialVersionUID = -6162371251085906606L;

        private final long noticeId;
        private final String title;
        private final String contents;

        @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
        private final LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
        private final LocalDateTime updatedAt;

        private final String createdBy;
        private final String updatedBy;

        public GetNotice(Notice notice) {
            this.noticeId = notice.getNoticeId();
            this.title = notice.getTitle();
            this.contents = notice.getContents();
            this.createdAt = notice.getCreatedAt();
            this.updatedAt = notice.getUpdatedAt();
            this.createdBy = notice.getCreatedBy();
            this.updatedBy = notice.getUpdatedBy();
        }
    }
}
