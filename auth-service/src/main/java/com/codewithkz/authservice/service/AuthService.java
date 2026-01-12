package com.codewithkz.authservice.service;

import com.codewithkz.authservice.dto.*;
import com.codewithkz.authservice.entity.RefreshToken;
import com.codewithkz.authservice.entity.Roles;
import com.codewithkz.authservice.entity.User;
import com.codewithkz.authservice.mapper.UserMapper;
import com.codewithkz.authservice.repository.AuthRepository;
import com.codewithkz.authservice.repository.RefreshTokenRepository;
import com.codewithkz.commoncore.exception.BadRequestException;
import com.codewithkz.commoncore.exception.DuplicateException;
import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.commoncore.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
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
        var user = repo.findByEmail(dto.getEmail()).orElseThrow(() ->  new BadRequestException("Invalid email or password"));

        var isValid = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if (!isValid) {
            throw new BadRequestException("Invalid email or password");
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var refreshEntity = RefreshToken
                .builder()
                .token(refreshToken)
                .expiredAt(Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()))
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
        log.info("Access token: {}", dto.getAccessToken());
        log.info("Refresh token: {}", dto.getRefreshToken());
        Instant now = Instant.now();
        var refreshEntity = repoRefresh
                .findByTokenAndIsRevokedFalse(dto.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Refresh token is invalid"));

        if(refreshEntity.getExpiredAt().isBefore(now)) {
            throw new UnauthorizedException("Refresh token is expired");
        }

        Claims claims = jwtService.extractToken(dto.getRefreshToken());

        Long userId = Long.parseLong(claims.getSubject());
        var user = repo.findById(userId).orElseThrow(() -> new UnauthorizedException("Token is valid or expired"));


        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        refreshEntity.setRevoked(true);
        repoRefresh.save(refreshEntity);

        log.info("Refresh token successfully");

        return TokenResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }


}
