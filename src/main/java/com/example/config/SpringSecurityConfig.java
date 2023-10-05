package com.example.config;

import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    public static String[] AUTH_WHITELIST = {"/api/v1/auth/**",
            "/api/v1/article/lastFive/**", "/api/v1/article/lastThree/**",
            "/api/v1/article/lastEight/**", "/api/v1/article/*/lan**",
            "/api/v1/article/lastFour/**", "/api/v1/article/mostRead/**",
            "/api/v1/article/tagName/**", "/api/v1/article/typeAndRegion/**",
            "/api/v1/article/region/**", "/api/v1/article/category/**",
            "/api/v1/article/view/**", "/api/v1/article/shared/**",
            "/api/v1/articleType/lan/**", "/api/v1/region/lan/**",
            "/api/v1/category/lan/**", "/api/v1/comment/getList/**",
            "/api/v1/comment/replyList/**"};


    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((c) -> {
                    c.requestMatchers(AUTH_WHITELIST).permitAll()
                            .requestMatchers("/api/v1/attach/**").permitAll()
                            .requestMatchers("/api/v1/articleLike/like/**").permitAll()
                            .requestMatchers("/api/v1/articleLike/dislike/**").permitAll()
                            .requestMatchers("/api/v1/articleLike/remove/**").permitAll()
                            .requestMatchers("/api/v1/commentLike/like/**").permitAll()
                            .requestMatchers("/api/v1/commentLike/dislike/**").permitAll()
                            .requestMatchers("/api/v1/commentLike/remove/**").permitAll()
                            .requestMatchers("/api/v1//api/v1/savedArticle/**").permitAll()
                            .anyRequest().authenticated();
                }).
                addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return MD5Util.encode(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
