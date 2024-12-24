package com.knitted.marketplace.models.item;

import com.knitted.marketplace.models.Contact;

import java.util.List;

public class Customer extends Contact {
    List<Order> orders;
}
