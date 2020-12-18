package com.wagoowagoo.fincat.api.product.repository;

import com.wagoowagoo.fincat.api.product.entity.ProductBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBookmarkRepository extends JpaRepository<ProductBookmark, Long> {
}
