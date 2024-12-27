package com.knitted.marketplace.models.item;

import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.order.Order;
import jakarta.persistence.*;

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
    private String description;
    private Double price;

    private Category category;
    private Subcategory subcategory;
    private TargetGroup targetgroup;
    private Size size;

    @OneToOne(mappedBy = "soldItem")
    private Order order;

    // Getters and Setters

    @Override
    public String toString() {
        return title + "(id: " + id + ")";
    }
}
