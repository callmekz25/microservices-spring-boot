package com.codewithkz.apigateway.filter;

import com.codewithkz.commoncore.constant.SecurityHeader;
import com.codewithkz.commoncore.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class GatewayTokenFilter implements GatewayFilter {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.internalSecret}")
    private String internalSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        log.info("Request {} {}", method, path);


        try {
            String token = this.extractJWTToken(exchange.getRequest());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String internalToken = Jwts.builder()
                    .setSubject(claims.getSubject())
                    .claim("role", claims.get("role"))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 60_000))
                    .signWith(
                            Keys.hmacShaKeyFor(internalSecret.getBytes(StandardCharsets.UTF_8)),
                            SignatureAlgorithm.HS256
                    )
                    .compact();

            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header(SecurityHeader.X_TOKEN, internalToken)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (ExpiredJwtException e) {
            return unauthorized(exchange, "Token expired");
        } catch (JwtException e) {
            return unauthorized(exchange, e.getMessage());
        }
    }

    private String extractJWTToken(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            throw new JwtException("Authorization header is missing");
        }

        List<String> headers = request.getHeaders().get("Authorization");
        if (headers.isEmpty()) {
            throw new JwtException("Authorization header is empty");
        }

        String token = headers.getFirst().substring(7);

        return token;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiResponse<?> body = ApiResponse.error(message);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }


}

