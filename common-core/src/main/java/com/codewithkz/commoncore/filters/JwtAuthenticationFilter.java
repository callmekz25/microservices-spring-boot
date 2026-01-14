package com.codewithkz.commoncore.filters;

import com.codewithkz.commoncore.constant.GatewayHeaders;
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
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    private final String internalSecret;

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return HttpMethod.GET.matches(request.getMethod())
                && matcher.match("/api/products/**", request.getRequestURI());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(GatewayHeaders.TOKEN);

        if (token == null) {
            formatResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing token");
            return;
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(internalSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            String role = claims.get("role", String.class);

            if (userId == null || role == null) {
                throw new JwtException("Invalid token");
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            request.setAttribute("userId", userId);
            request.setAttribute("role", role);

            filterChain.doFilter(request, response);



        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            formatResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }


    }


    private void formatResponse(
            HttpServletResponse response,
            int status,
            String message
    ) throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> body = ApiResponse.error(message);

        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
    }

}
