package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private byte[] imageData;


    //Getters and Setters

    public String getName() {
        return filename != null ? filename : "Shop has no picture";
    }

    public void setName(String name) {
        this.filename = name;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
