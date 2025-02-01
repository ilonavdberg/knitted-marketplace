package com.knitted.marketplace.integration;

import com.knitted.marketplace.KnittedApplication;
import com.knitted.marketplace.repositories.ShopRepository;
import com.knitted.marketplace.services.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KnittedApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "user", roles = {"USER"})
public class CreateShopIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ShopService shopService;

    @Autowired
    ShopRepository shopRepository;

    @BeforeEach
    void setUp() {
//        shopRepository.deleteAll();
    }

    @Test
    void testCreateShop() throws Exception {
        // Read the image file from the project directory
        Path imagePath = Paths.get("src/main/resources/static/images/shop_logo_1.jpg");
        byte[] imageBytes = Files.readAllBytes(imagePath);

        // Create a MockMultipartFile using the actual image file
        MockMultipartFile shopImage = new MockMultipartFile(
                "image",
                "shop_logo_1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes
        );

        mockMvc.perform(multipart("/shops")
                .file(shopImage)
                .param("name", "test shop")
                .param("description", "this is a description for the test shop that should pass validation constraints")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("test shop"))
                .andExpect(jsonPath("$.description").value("this is a description for the test shop that should pass validation constraints"))
                .andExpect(jsonPath("$.owner").value("testuser"));
    }
}
