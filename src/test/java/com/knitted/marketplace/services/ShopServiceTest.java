package com.knitted.marketplace.services;

import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopService shopService;

    private Shop shop;


    @BeforeEach
    void setUp() {
        shop = new Shop(1L, "test shop");
    }

    @Test
    void getShop() {
        // Arrange
        Long id = 1L;
        when(shopRepository.findById(id)).thenReturn(Optional.ofNullable(shop));

        // Act
        Shop fetchedShop = shopService.getShop(id);

        // Assert
        assertEquals(shop, fetchedShop);
    }
}