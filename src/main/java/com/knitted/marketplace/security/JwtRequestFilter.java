package com.knitted.marketplace.security;

import com.knitted.marketplace.utils.Parser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtRequestFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;
        Long userId = null;
        Long shopId = null;
        List<GrantedAuthority> roles = new ArrayList<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = jwtService.extractId(jwt);
            shopId = jwtService.extractShopId(jwt);
            roles = Parser.toAuthorities(jwtService.extractRoles(jwt));
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.validateToken(jwt)) {
                CustomPrincipal principal = new CustomPrincipal(userId, shopId);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal, null, roles
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
