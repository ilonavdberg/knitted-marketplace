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

    private ItemStatus status = ItemStatus.NOT_PUBLISHED;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

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


    public void addPhotos(List<ImageFile> newPhotos) {
        photos.addAll(newPhotos);
    }



    // Getters and Setters


    public Long getId() {
        return id;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public Shop getShop() {
        return shop;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public TargetGroup getTargetgroup() {
        return targetgroup;
    }

    public ClothingSize getClothingSize() {
        return clothingSize;
    }

    public List<ImageFile> getPhotos() {
        return photos;
    }

    public Order getOrder() {
        return order;
    }

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

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return title + "(id: " + id + ")";
    }
}
