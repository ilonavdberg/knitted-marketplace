package com.knitted.marketplace.models;

import com.knitted.marketplace.models.item.Item;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 300)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageFile shopPicture;

    @OneToMany(mappedBy = "shop")
    private List<Item> items = new ArrayList<>();

    // TODO: Set optional is false when user authentication guarantees every shop has on owner.
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Contact owner;


    // Constructors

    public Shop() {}

    public Shop(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Shop(Long id, String name, String description, ImageFile shopPicture, List<Item> items, Contact owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.shopPicture = shopPicture;
        this.items = items;
        this.owner = owner;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ImageFile getShopPicture() {
        return shopPicture;
    }

    public List<Item> getItems() {
        return items;
    }

    public Contact getOwner() {
        return owner;
    }

    public List<String> getItemsToString() {
        return items.stream().map(Object::toString).toList();
    }

    //TODO: Remove this fallback once user authentication guarantees that every shop has an owner.
    public String getOwnerToString() {
        return (owner == null) ? "No owner assigned" : owner.toString();
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
    }

    public void setShopPicture(ImageFile shopPicture) {
        this.shopPicture = shopPicture;
    }
}
