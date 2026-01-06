//package com.codewithkz.apigateway.config;
//
//import com.codewithkz.apigateway.filter.JwtFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class GatewayConfig {
//    private final JwtFilter jwtFilter;
//
//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
//        return builder.routes()
//
//                .route("auth-service", r -> r
//                        .path("/api/auth/**")
//                        .uri("lb://auth-service"))
//
//                .route("product-services-public", r -> r
//                        .path("/api/products/**")
//                        .and()
//                        .method("GET")
//                        .uri("lb://product-service"))
//
//                .route("product-service-protected", r -> r
//                        .path("/api/products/**")
//                        .filters(f -> f.filter(jwtFilter))
//                        .uri("lb://product-service"))
//
//                .route("inventory-service", r -> r
//                        .path("/api/inventories/**")
//                        .filters(f -> f.filter(jwtFilter))
//                        .uri("lb://inventory-service"))
//
//                .route("order-service", r -> r
//                        .path("/api/orders/**")
//                        .filters(f -> f.filter(jwtFilter))
//                        .uri("lb://order-service"))
//
//                .route("payment-service", r -> r
//                        .path("/api/payments/**")
//                        .filters(f -> f.filter(jwtFilter))
//                        .uri("lb://payment-service"))
//                .build();
//    }
//}
