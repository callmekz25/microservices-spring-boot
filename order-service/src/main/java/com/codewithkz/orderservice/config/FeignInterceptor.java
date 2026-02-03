package com.codewithkz.orderservice.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
@Slf4j
public class FeignInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            log.info("RequestInterceptor running...");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth instanceof JwtAuthenticationToken jwtAuth) {
                String token = jwtAuth.getToken().getTokenValue();
                log.info("Token from security context: {}", token);
                requestTemplate.header("Authorization", "Bearer " + token);
            } else {
                log.warn("No JWT authentication found in security context");
            }
        };
    }
}
