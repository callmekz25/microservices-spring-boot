package com.codewithkz.apigateway.filter;

import com.codewithkz.commoncore.constant.GatewayHeaders;
import com.codewithkz.commoncore.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    private List<String> publicApi = List.of(
            "/api/auth/login",
            "/api/auth/register"
    );

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.internalSecret}")
    private String internalSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String path = exchange.getRequest().getPath().value();
            String method = exchange.getRequest().getMethod().name();
            log.info("Request to {}", path);

            if ("GET".equalsIgnoreCase(method) && pathMatcher.match("/api/products/**", path)) {
                return chain.filter(exchange);
            }

            for (String publicPath : publicApi) {
                if (pathMatcher.match(publicPath, path)) {
                    return chain.filter(exchange);
                }
            }

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange, "Missing token");
            }

            String token = authHeader.substring(7);

            try {
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

                log.info("Internal Token: {}", internalToken);


                ServerHttpRequest request = exchange.getRequest()
                        .mutate()
                        .headers(httpHeaders -> {
                            httpHeaders.remove(GatewayHeaders.TOKEN);
                            httpHeaders.add(GatewayHeaders.TOKEN, internalToken);
                        })
                        .build();
                log.info("Header Token {}", request.getHeaders().getFirst(GatewayHeaders.TOKEN));
                return chain.filter(exchange.mutate().request(request).build());

            }
            catch (ExpiredJwtException e) {
                return unauthorized(exchange, "Token is expired");
            }
            catch (JwtException e) {
                return unauthorized(exchange, "Token is invalid");
            }
        };
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


    public static class Config {}
}

