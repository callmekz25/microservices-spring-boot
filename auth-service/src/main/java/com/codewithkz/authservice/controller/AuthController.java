package com.codewithkz.authservice.controller;

import com.codewithkz.authservice.core.response.ApiResponse;
import com.codewithkz.authservice.dto.*;
import com.codewithkz.authservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("me")
    public ResponseEntity<ApiResponse<UserDto>> getMe() {
        var user = service.getMe();

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody CreateDto dto) {
        var result = service.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody LoginDto dto) {
        var result = service.login(dto);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("refreshToken")
    public ResponseEntity<ApiResponse<TokenResponseDto>> refreshToken(HttpServletRequest request, @RequestBody RefreshTokenRequestDto dto) {
        var authHeader = request.getHeader("authorization");
        var accessToken = authHeader.substring(7);

        var result = service.refreshToken(RefreshTokenDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(dto.getRefreshToken())
                .build());

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
