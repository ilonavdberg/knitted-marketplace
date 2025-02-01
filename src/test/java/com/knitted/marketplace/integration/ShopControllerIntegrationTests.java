package com.knitted.marketplace.integration;

import com.knitted.marketplace.models.Contact;
import com.knitted.marketplace.models.ImageFile;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.ContactRepository;
import com.knitted.marketplace.repositories.ShopRepository;
import com.knitted.marketplace.repositories.UserRepository;
import com.knitted.marketplace.security.JwtService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ShopControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private ImageFile image;
    private Contact contact;
    private User user;


    @BeforeEach
    void setup() throws IOException {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.addRole("USER");
        user = userRepository.save(user);
        this.user = user;

        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setEmail("john.doe@example.com");
        contact.setPhone("0612345678");
        contact.setUser(user);
        contact = contactRepository.save(contact);
        this.contact = contact;

        user.setContact(contact);
        userRepository.save(user);

        Path imagePath = Paths.get("src/main/resources/static/images/shop_logo_1.jpg");
        byte[] imageBytes = Files.readAllBytes(imagePath);

        ImageFile shopImage = new ImageFile();
        shopImage.setFilename("shop_logo_1");
        shopImage.setExtension("jpg");
        shopImage.setImageData(imageBytes);

        this.image = shopImage;
    }

    @Test
    void testGetShopSummary() throws Exception {
        Shop shop = new Shop();
        shop.setName("Test Shop");
        shop.setDescription("shop description for the test shop");
        shop.setOwner(contact);
        shop.setShopPicture(image);
        shop = shopRepository.save(shop);

        mockMvc.perform(get(BASE_URL + "/shops/" + shop.getId() + "/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Shop"))
                .andExpect(jsonPath("$.description").value("shop description for the test shop"))
                .andExpect(jsonPath("$.shopPicture").exists())
                .andExpect(jsonPath("$.shopPicture.filename").value(shop.getShopPicture().getFilename()))
                .andExpect(jsonPath("$.shopPicture.extension").value(shop.getShopPicture().getExtension()))
                .andExpect(jsonPath("$.shopPicture.base64Image").exists());
    }

    @Test
    void testCreateShop() throws Exception {
        String fakeAuthHeader = "Bearer " + jwtService.generateToken(user, user.getRoles());

        MockMultipartFile mockImage = new MockMultipartFile(
                "uploadedImage",
                image.getFilename(),
                MediaType.IMAGE_JPEG_VALUE,
                image.getImageData()
        );

        mockMvc.perform(multipart(BASE_URL + "/shops")
                .file(mockImage)
                .param("name", "Test Shop")
                .param("description", "shop description for the test shop")
                .header("Authorization", fakeAuthHeader)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Shop"))
                .andExpect(jsonPath("$.description").value("shop description for the test shop"))
                .andExpect(jsonPath("$.shopPicture").exists())
                .andExpect(jsonPath("$.shopPicture.base64Image").exists());
    }
}
