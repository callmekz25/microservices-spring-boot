package com.codewithkz.commoncore.filters;

import com.codewithkz.commoncore.constant.SecurityHeader;
import com.codewithkz.commoncore.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class GatewaySecurityFilter extends OncePerRequestFilter {

//    private final ObjectMapper objectMapper;
//
//    private final String internalSecret;

    private static String ROLE_PREFIX = "ROLE_";



//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String token = request.getHeader(SecurityHeader.X_TOKEN);
//
//
//
//        if(token != null) {
//            try {
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(internalSecret.getBytes())
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                String userId = claims.getSubject();
//                String role = claims.get("role", String.class);
//
//
//                Authentication auth = new UsernamePasswordAuthenticationToken(
//                        userId,
//                        null,
//                        List.of(new SimpleGrantedAuthority(ROLE_PREFIX + role))
//                );
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            } catch (JwtException e) {
//                SecurityContextHolder.clearContext();
//                formatResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
//            }
//        }
//        filterChain.doFilter(request, response);
//
//
//
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String sub = request.getHeader(SecurityHeader.X_User_ID);
        String role = request.getHeader(SecurityHeader.X_ROLES);



        if(sub != null && role != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    sub,
                    null,
                    List.of(new SimpleGrantedAuthority(role))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);

    }



//    private void formatResponse(
//            HttpServletResponse response,
//            int status,
//            String message
//    ) throws IOException {
//
//        response.setStatus(status);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        ApiResponse<?> body = ApiResponse.error(message);
//
//        String json = objectMapper.writeValueAsString(body);
//        response.getWriter().write(json);
//    }

}
