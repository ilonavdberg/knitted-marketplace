package com.knitted.marketplace.sampledata;

import org.springframework.stereotype.Service;


@Service
public class SampleDataService {
    private final ShopDataGenerator shopDataGenerator;
    private final ItemDataGenerator itemDataGenerator;
    private final ReviewDataGenerator reviewDataGenerator;

    public SampleDataService(ShopDataGenerator shopDataGenerator, ItemDataGenerator itemDataGenerator, ReviewDataGenerator reviewDataGenerator) {
        this.shopDataGenerator = shopDataGenerator;
        this.itemDataGenerator = itemDataGenerator;
        this.reviewDataGenerator = reviewDataGenerator;
    }

    public void loadSampleData() {
        System.out.println("Initializing loading sample data...");

        System.out.println("Start generating item data");
        itemDataGenerator.generate();
//        System.out.println("Start generating review data");
//        reviewDataGenerator.generate();

        System.out.println("Finished generating sample data...");
    }

}
