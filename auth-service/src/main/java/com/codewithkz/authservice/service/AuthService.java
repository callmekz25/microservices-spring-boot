package com.codewithkz.authservice.service;

import com.codewithkz.authservice.core.exception.BadRequestException;
import com.codewithkz.authservice.core.exception.DuplicateException;
import com.codewithkz.authservice.core.exception.NotFoundException;
import com.codewithkz.authservice.core.exception.UnauthorizedException;
import com.codewithkz.authservice.dto.*;
import com.codewithkz.authservice.entity.RefreshToken;
import com.codewithkz.authservice.entity.Roles;
import com.codewithkz.authservice.entity.User;
import com.codewithkz.authservice.mapper.UserMapper;
import com.codewithkz.authservice.repository.AuthRepository;
import com.codewithkz.authservice.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository repo;
    private final RefreshTokenRepository repoRefresh;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public UserDto getMe() {
        var user = repo.findById(1L).orElseThrow(() -> new NotFoundException("User not found"));

        return mapper.toDto(user);
    }


    public UserDto register(CreateDto dto) {
        var existed = repo.findByEmail(dto.getEmail());

        if (existed.isPresent()) {
            throw new DuplicateException("Email already exists");
        }

        User user = mapper.toEntity(dto);
        user.setRole(Roles.USER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        repo.save(user);

        return mapper.toDto(user);

    }

    @Transactional
    public TokenResponseDto login(LoginDto dto) {
        var user = repo.findByEmail(dto.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));

        var isValid = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if (!isValid) {
            throw new BadRequestException("Invalid email or password");
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var refreshEntity = RefreshToken
                .builder()
                .token(refreshToken)
                .expiredAt(Instant.now())
                .user(user)
                .build();

        repoRefresh.save(refreshEntity);


        return TokenResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }


    @Transactional
    public TokenResponseDto refreshToken(RefreshTokenDto dto) {
        Instant now = Instant.now();
        var refreshEntity = repoRefresh
                .findByTokenAndIsRevokedFalseAndExpiredAtAfter(dto.getRefreshToken(), now)
                .orElseThrow(() -> new UnauthorizedException("Refresh token is invalid"));


        Claims claims = jwtService.extractToken(dto.getRefreshToken());

        Long userId = Long.parseLong(claims.getSubject());
        var user = repo.findById(userId).orElseThrow(() -> new UnauthorizedException("Token is valid or expired"));


        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return TokenResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }


}
