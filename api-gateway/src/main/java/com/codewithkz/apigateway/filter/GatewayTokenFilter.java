//package com.codewithkz.apigateway.filter;
//
//import com.codewithkz.commoncore.constant.SecurityHeader;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//@Component
//@Slf4j
//public class GatewayTokenFilter implements GatewayFilter {
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        return exchange.getPrincipal()
//                .cast(Authentication.class)
//                .flatMap(auth -> {
//
//                    if (auth instanceof JwtAuthenticationToken jwtAuth) {
//                        Jwt jwt = jwtAuth.getToken();
//
//                        ServerHttpRequest mutatedRequest = exchange.getRequest()
//                                .mutate()
//                                .header(SecurityHeader.X_User_ID, jwt.getSubject())
//                                .header(SecurityHeader.X_ROLES, "ROLE_ADMIN")
//                                .build();
//
//                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
//                    }
//
//                    return chain.filter(exchange);
//                });
//    }
//
//
//
//}
//
