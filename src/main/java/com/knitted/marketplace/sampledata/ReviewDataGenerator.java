package com.knitted.marketplace.sampledata;

import com.knitted.marketplace.dtos.review.ReviewRequestDto;
import com.knitted.marketplace.services.OrderService;
import com.knitted.marketplace.services.ReviewService;
import org.springframework.stereotype.Component;


@Component
public class ReviewDataGenerator {

    private final ReviewService reviewService;
    private final OrderService orderService;

    public ReviewDataGenerator(ReviewService reviewService, OrderService orderService) {
        this.reviewService = reviewService;
        this.orderService = orderService;
    }

//    public void generate() {
//        //create some orders
//        orderService.orderItem(1L);
//        orderService.orderItem(2L);
//        orderService.orderItem(3L);
//        orderService.orderItem(4L);
//        orderService.orderItem(7L);
//        orderService.orderItem(10L);
//        orderService.orderItem(13L);
//
//        //create review requests
//        for (int i = 1; i <8 ; i++) {
//            var order = orderService.getOrder((long) i);
//            System.out.println(order);
//            var request = new ReviewRequestDto(order, 4, "review" + i, "This is a review to describe how I perceive the product. I can address points about the quality of the product, contact with the seller or how the delivery went");
//            reviewService.save((long) i, request);
//        }
//    }
}
