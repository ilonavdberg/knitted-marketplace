package com.knitted.marketplace.sampledata;

import org.springframework.stereotype.Service;


@Service
public class SampleDataService {
    private final ShopDataGenerator shopDataGenerator;
    private final ItemDataGenerator itemDataGenerator;

    public SampleDataService(ShopDataGenerator shopDataGenerator, ItemDataGenerator itemDataGenerator) {
        this.shopDataGenerator = shopDataGenerator;
        this.itemDataGenerator = itemDataGenerator;
    }

    public void loadSampleData() {
        System.out.println("Initializing loading sample data...");

        System.out.println("Start loading shop data");
        shopDataGenerator.generate();
        System.out.println("Start loading item data");
        itemDataGenerator.generate();
        System.out.println("Finished loading sample data...");
    }

}
