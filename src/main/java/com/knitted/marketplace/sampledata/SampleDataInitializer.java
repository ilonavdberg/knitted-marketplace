package com.knitted.marketplace.sampledata;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SampleDataInitializer {

    private final SampleDataService sampleDataService;

    public SampleDataInitializer(SampleDataService sampleDataService) {
        this.sampleDataService = sampleDataService;
    }

    @PostConstruct
    public void initialize() {
//        sampleDataService.loadSampleData();
    }
}
