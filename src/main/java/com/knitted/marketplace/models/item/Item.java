package com.knitted.marketplace.models.item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ItemStatus status;

    private String title;
    private String description;
    private Double price;

    private Category category;
    private Subcategory subcategory;
    private TargetGroup targetgroup;
    private Size size;
}
