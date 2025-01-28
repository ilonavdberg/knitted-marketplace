package com.knitted.marketplace.config;

import com.knitted.marketplace.security.JwtRequestFilter;
import com.knitted.marketplace.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.knitted.marketplace.config.ApiConfig.BASE_URL;

@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public SecurityConfig(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                //TODO: add authorization rules and change permitAll to denyAll
//                                //public endpoints
                                .requestMatchers(BASE_URL + "/auth/**").permitAll() // authentication
                                .requestMatchers(HttpMethod.GET, BASE_URL + "/items/**").permitAll() // view product catalog
                                .requestMatchers(HttpMethod.GET, BASE_URL + "/shops/*/*").permitAll() // view shop content

//
//
//                                //secure endpoints - all users
                                .requestMatchers(HttpMethod.GET, BASE_URL + "/customer").hasAuthority("ROLE_USER") // get account details
                                .requestMatchers(HttpMethod.POST, BASE_URL + "/items/*/order").hasAuthority("ROLE_USER") // order item
                                .requestMatchers(HttpMethod.POST, BASE_URL + "/order/*/review").hasAuthority("ROLE_USER") // create review
                                .requestMatchers(HttpMethod.POST, BASE_URL + "/shops").hasAuthority("ROLE_USER") // create shop
//
//                                //secure endpoints - shop owners
//                                .requestMatchers(HttpMethod.POST, BASE_URL + "/shops/*/items").hasAuthority("ROLE_SHOP_OWNER") // create item
//                                .requestMatchers(HttpMethod.PUT, BASE_URL + "/items/*/**").hasAuthority("ROLE_SHOP_OWNER") // update item
//                                .requestMatchers(HttpMethod.POST, BASE_URL + "/reviews/*/reaction").hasAuthority("ROLE_SHOP_OWNER") // react to review
//
//                                .anyRequest().denyAll()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtRequestFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> {
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
