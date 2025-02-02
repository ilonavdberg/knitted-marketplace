package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.shop.ShopCreatedResponseDto;
import com.knitted.marketplace.dtos.shop.ShopRequestDto;
import com.knitted.marketplace.exception.exceptions.RecordAlreadyExistsException;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.ShopMapper;
import com.knitted.marketplace.models.Contact;
import com.knitted.marketplace.models.Shop;
import com.knitted.marketplace.models.User;
import com.knitted.marketplace.repositories.ShopRepository;
import com.knitted.marketplace.security.JwtService;
import com.knitted.marketplace.utils.Parser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final JwtService jwtService;
    private final UserService userService;

    public ShopService(ShopRepository shopRepository, JwtService jwtService, UserService userService) {
        this.shopRepository = shopRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Transactional
    public ShopCreatedResponseDto createShop(ShopRequestDto request, String authHeader) {

        // Get user
        String token = Parser.toToken(authHeader);
        Long userId = jwtService.extractId(token);
        User user = userService.getUserById(userId);

        // validate if user has no shop yet
        if (user.getContact().getShop() != null) {
            throw new RecordAlreadyExistsException("A shop already exists for this user.");
        }

        // Create shop
        Shop shop = ShopMapper.toShop(request);
        Contact owner = user.getContact();
        shop.setOwner(owner);
        Shop savedShop = shopRepository.save(shop);

        // Update user role
        user.addRole("SHOP_OWNER");
        userService.saveUser(user);

        //generate new token
        String newToken = jwtService.generateToken(user, user.getRoles());

        return new ShopCreatedResponseDto(savedShop, newToken);
    }

    public Shop getShop(Long id) {
        return shopRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
