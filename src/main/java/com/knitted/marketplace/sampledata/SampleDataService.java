package com.knitted.marketplace.sampledata;

import org.springframework.stereotype.Service;


@Service
public class SampleDataService {
    private final ItemDataGenerator itemDataGenerator;

    public SampleDataService(ItemDataGenerator itemDataGenerator) {
        this.itemDataGenerator = itemDataGenerator;
    }

    public void loadSampleData() {
        System.out.println("Initializing loading sample data...");

        System.out.println("Start generating item data");
        itemDataGenerator.generate();

        System.out.println("Finished generating sample data...");
    }
}
