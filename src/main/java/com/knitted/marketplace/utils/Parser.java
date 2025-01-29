package com.knitted.marketplace.utils;

import com.knitted.marketplace.models.item.ClothingSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static Double toDouble(String stringInput) throws NumberFormatException {
        return Double.valueOf(stringInput);
    }

    public static List<ClothingSize> toSizeList(String sizes) {
        if (sizes.isEmpty()) {
            return List.of(); // Return an empty list if the input is empty
        }

        return Arrays.stream(sizes.split(","))
                .map(ClothingSize::fromString)
                .toList();
    }

    public static List<GrantedAuthority> toAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    public static String toToken(String authHeader) {
        return authHeader.replace("Bearer ", "");
    }
}
