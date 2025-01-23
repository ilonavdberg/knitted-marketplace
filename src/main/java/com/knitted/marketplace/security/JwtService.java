package com.knitted.marketplace.security;

import com.knitted.marketplace.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Service
public class JwtService {
    @Value("${jwt.secret.key")
    private String JWT_SECRET_KEY;

    @Value("#{T(java.lang.Long).valueOf('${jwt.expiration}')}")
    private Long JWT_EXPIRATION_MS;

    public String generateToken(User user, List<String> roles) {
        Key signingKey = getSigningKey();

        Long shopId = user.getContact().getShop() != null ? user.getContact().getShop().getId() : null;

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("roles", roles)
                .claim("shop", shopId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build();
            parser.parseClaimsJws(token);
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public List<String> extractRoles(String token) {
        return getClaim(token, "roles", List.class);
    }

    public Long extractId(String token) {
        return Long.valueOf(getClaim(token, Claims.SUBJECT, String.class));
    }

    public Long extractShopId(String token) {
        return getClaim(token, "shop", Long.class);
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY); // Decode the base64 secret key into bytes
        return Keys.hmacShaKeyFor(keyBytes); // Use Keys.hmacShaKeyFor to create the signing key
    }

    private <T> T getClaim(String token, String key, Class<T> type) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        return type.cast(claims.get(key));
    }
}
