package com.knitted.marketplace.models;

import jakarta.persistence.*;

@Entity
@Table(name = "image_files")
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Lob
    private byte[] imageData;


    //Getters and Setters


    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename != null ? filename : "Shop has no picture";
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
