package com.codewithkz.apigateway.config;

//import com.codewithkz.apigateway.filter.GatewayTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {
//    private final GatewayTokenFilter gatewayTokenFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route( p -> p
                        .path("/api/auth/**")
                        .uri("lb://auth-service"))

                .route(p -> p
                        .path("/api/products/**")
                        .and()
                        .method("GET")
                        .uri("lb://product-service"))

                .route(p -> p
                        .path("/api/products/**")
                        .and()
                        .method("POST", "PUT", "DELETE")
//                        .filters(f -> f.filter(gatewayTokenFilter))
                        .uri("lb://product-service"))

                .route(p -> p
                        .path("/api/inventories/**")
//                        .filters(f -> f.filter(gatewayTokenFilter))
                        .uri("lb://inventory-service"))

                .route(p -> p
                        .path("/api/orders/**")
//                        .filters(f -> f.filter(gatewayTokenFilter))
                        .uri("lb://order-service"))

                .route(p -> p
                        .path("/api/payments/**")
//                        .filters(f -> f.filter(gatewayTokenFilter))
                        .uri("lb://payment-service"))
                .build();
    }
}
