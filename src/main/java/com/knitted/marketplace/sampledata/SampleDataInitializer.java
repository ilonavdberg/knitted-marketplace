package com.knitted.marketplace.sampledata;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    public void initializeImages() {
        try {
            imageDataGenerator.saveImages(); // Ensure images exist first
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @ConditionalOnProperty(name = "app.load-sample-data", havingValue = "true")
    public void initializeSampleData() {
        sampleDataService.loadSampleData(); // This runs AFTER data.sql is loaded
    }
}
