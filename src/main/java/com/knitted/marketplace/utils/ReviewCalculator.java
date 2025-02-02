package com.knitted.marketplace.utils;

import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.item.ItemStatus;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReviewCalculator {

    @Transactional
    public Long calculateNumberOfReviews(Shop shop) {
        Hibernate.initialize(shop.getItems());

        shop.getItems().forEach(item -> {
            Hibernate.initialize(item.getOrder());
            if (item.getOrder() != null) {
                Hibernate.initialize(item.getOrder().getReview());
            }
        });

        return shop.getItems().stream()
                .filter(item -> item.getStatus().equals(ItemStatus.SOLD))
                .filter(item -> item.getOrder() != null && item.getOrder().getReview() != null)
                .count();
    }

    @Transactional
    public Double calculateAverageRating(Shop shop) {
        Hibernate.initialize(shop.getItems());

        shop.getItems().forEach(item -> {
            Hibernate.initialize(item.getOrder());
            if (item.getOrder() != null) {
                Hibernate.initialize(item.getOrder().getReview());
            }
        });

        return shop.getItems().stream()
                .filter(item -> item.getStatus().equals(ItemStatus.SOLD))
                .filter(item -> item.getOrder() != null && item.getOrder().getReview() != null)
                .mapToInt(item -> item.getOrder().getReview().getRating())
                .average()
                .orElse(0);
    }
}
