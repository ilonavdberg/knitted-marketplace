package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] imageData;

    //Getters and Setters


    public String getName() {
        return name != null ? name : "Shop has no picture";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
