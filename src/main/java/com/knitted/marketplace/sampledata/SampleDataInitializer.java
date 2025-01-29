package com.knitted.marketplace.sampledata;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleDataInitializer {

    private final SampleDataService sampleDataService;
    private final ImageDataGenerator imageDataGenerator;

    public SampleDataInitializer(SampleDataService sampleDataService, ImageDataGenerator imageDataGenerator) {
        this.sampleDataService = sampleDataService;
        this.imageDataGenerator = imageDataGenerator;
    }

    @PostConstruct
    public void initialize() {
//        sampleDataService.loadSampleData();
        try {
            imageDataGenerator.saveImages();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
