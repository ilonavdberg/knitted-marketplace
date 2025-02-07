package com.knitted.marketplace.repositories;

import com.knitted.marketplace.models.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    @Query("""
    SELECT r
    FROM Review r
    JOIN r.order o
    JOIN o.soldItem i
    JOIN i.shop s
    WHERE s.id = :shopId
""")
    Page<Review> findByShopId(Long shopId, Pageable pageable);
}
