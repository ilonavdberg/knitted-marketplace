package com.knitted.marketplace.integration;

import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.repositories.ItemRepository;
import com.knitted.marketplace.services.ItemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAllItemsForSaleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setup() {
        itemRepository.deleteAll();

        List<Item> testItems = List.of(
                new Item(
                        1L,
                        "test item 1",
                        "this is a description for a test item",
                        25.50,
                        Category.TOYS,
                        Subcategory.BABY_TOYS,
                        TargetGroup.UNSPECIFIED,
                        ClothingSize.UNSPECIFIED,
                        null,
                        null
                ),
                new Item(
                        2L,
                        "test item 1",
                        "this is a description for a test item",
                        45.50,
                        Category.TOYS,
                        Subcategory.BABY_TOYS,
                        TargetGroup.UNSPECIFIED,
                        ClothingSize.UNSPECIFIED,
                        null,
                        null
                )
        );
        itemRepository.saveAll(testItems);
    }


    @Test
    @Transactional
    void testGetAllItemsForSale() throws Exception {
        mockMvc.perform(get(BASE_URL + "items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
