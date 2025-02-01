package com.knitted.marketplace.integration;

import com.knitted.marketplace.models.Contact;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.ContactRepository;
import com.knitted.marketplace.repositories.ShopRepository;
import com.knitted.marketplace.repositories.UserRepository;
import com.knitted.marketplace.services.ShopService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetShopIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    private Long shopId;

    @BeforeEach
    void setup() throws IOException {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.addRole("USER");
        user = userRepository.save(user);

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john.doe@example.com");
        contact.setPhone("0612345678");
        contact.setUser(user);
        contact = contactRepository.save(contact);

        Shop shop = new Shop();
        shop.setName("testShop");
        shop.setDescription("shop description for the test shop");
        shop.setOwner(contact);
        shop = shopRepository.save(shop);

        Path imagePath = Paths.get("src/main/resources/static/images/shop_logo_1.jpg");
        byte[] imageBytes = Files.readAllBytes(imagePath);

        ImageFile shopImage = new ImageFile();
        shopImage.setFilename("shop_logo_1");
        shopImage.setExtension("jpg");
        shopImage.setImageData(imageBytes);

        shop.setShopPicture(shopImage);

        this.shopId = shop.getId();
    }

    @Test
    void testGetShopSummary() throws Exception {
        mockMvc.perform(get(BASE_URL + "/shops/" + shopId + "/profile"))
                .andExpect(status().isOk());
    }
}
