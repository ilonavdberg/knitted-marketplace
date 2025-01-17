package com.knitted.marketplace.sampledata;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.models.item.ItemStatus;
import com.knitted.marketplace.services.ItemService;
import com.knitted.marketplace.services.OrderService;
import com.knitted.marketplace.services.ReviewService;
import org.springframework.stereotype.Component;

@Component
public class ReviewDataGenerator {

    private final ItemService itemService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    public ReviewDataGenerator(ItemService itemService, ReviewService reviewService, OrderService orderService) {
        this.itemService = itemService;
        this.reviewService = reviewService;
        this.orderService = orderService;
    }

    //stap 1. verander status van een item naar Sold
    // updateItemStatus(id, ItemStatus.Sold)

    //stap 2. plaats een review
    //create review request dto
    //save in review repository(orderId, request dto)


    public void generate() {
        //create some orders
        itemService.updateItemStatus(1L, ItemStatus.SOLD);
        itemService.updateItemStatus(2L, ItemStatus.SOLD);
        itemService.updateItemStatus(3L, ItemStatus.SOLD);
        itemService.updateItemStatus(4L, ItemStatus.SOLD);
        itemService.updateItemStatus(7L, ItemStatus.SOLD);
        itemService.updateItemStatus(10L, ItemStatus.SOLD);
        itemService.updateItemStatus(13L, ItemStatus.SOLD);

        //create review requests
        for (int i = 1; i <8 ; i++) {
            var order = orderService.getOrder((long) i);
            var request = new ReviewRequestDto(order, i%2 + 3, "review" + i, "This is a review to describe how I perceive the product. I can address points about the quality of the product, contact with the seller or how the delivery went");
            reviewService.save((long) i, request);
        }
    }






}
