package com.wagoowagoo.fincat.api.notice.repository;

import com.wagoowagoo.fincat.api.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
