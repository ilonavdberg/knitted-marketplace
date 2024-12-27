package com.knitted.marketplace.models.item;

import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;

import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    private ItemStatus status;
    private String title;

    @Column(length = 300)
    private String description;

    private Double price;

    private Category category;
    private Subcategory subcategory;
    private TargetGroup targetgroup;
    private ClothingSize clothingSize;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "item_id")
    private List<ImageFile> photos = new ArrayList<>();

    @OneToOne(mappedBy = "soldItem")
    private Order order;


    // Getters and Setters

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public void setTargetgroup(TargetGroup targetgroup) {
        this.targetgroup = targetgroup;
    }

    public void setClothingSize(ClothingSize clothingSize) {
        this.clothingSize = clothingSize;
    }

    public void setPhotos(List<ImageFile> photos) {
        this.photos = photos;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return title + "(id: " + id + ")";
    }
}
