package com.codewithkz.authservice.service.impl;

import com.codewithkz.authservice.dto.*;
import com.codewithkz.authservice.entity.RefreshToken;
import com.codewithkz.authservice.entity.Roles;
import com.codewithkz.authservice.entity.User;
import com.codewithkz.authservice.entity.UserPrincipal;
import com.codewithkz.authservice.mapper.UserMapper;
import com.codewithkz.authservice.repository.AuthRepository;
import com.codewithkz.authservice.repository.RefreshTokenRepository;
import com.codewithkz.authservice.service.AuthService;
import com.codewithkz.commoncore.exception.BadRequestException;
import com.codewithkz.commoncore.exception.DuplicateException;
import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.commoncore.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository repo;
    private final RefreshTokenRepository repoRefresh;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public UserDto getMe() {
        var user = repo.findById(1L).orElseThrow(() -> new NotFoundException("User not found"));

        return mapper.toDto(user);
    }


    @Override
    public UserDto register(CreateDto dto) {
        var existed = repo.findByEmail(dto.getEmail());

        if (existed.isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        String keycloakId = null;




        User user = mapper.toEntity(dto);
        user.setRole(Roles.ADMIN);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        repo.save(user);

        return mapper.toDto(user);

    }

    @Override
    @Transactional
    public AccessTokenDto login(AccountCredential dto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.getUsername(),
                                dto.getPassword()
                        )
                );


        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        User user = repo.findById(principal.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        var accessToken = jwtService.generateAccessToken(principal);
        var refreshToken = jwtService.generateRefreshToken(user.getId());



        var refreshEntity = RefreshToken
                .builder()
                .token(refreshToken)
                .expiredAt(Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()))
                .user(user)
                .build();

        repoRefresh.save(refreshEntity);


        return AccessTokenDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }




    @Override
    @Transactional
    public AccessTokenDto refreshToken(RefreshTokenDto dto) {

        Instant now = Instant.now();

        var refreshEntity = repoRefresh
                .findByTokenAndIsRevokedFalse(dto.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Refresh token is invalid"));

        if(refreshEntity.getExpiredAt().isBefore(now)) {
            throw new UnauthorizedException("Refresh token is expired");
        }

        User user = refreshEntity.getUser();

        refreshEntity.setRevoked(true);
        repoRefresh.save(refreshEntity);

        UserPrincipal principal = new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name()
        );

        var accessToken = jwtService.generateAccessToken(principal);
        var refreshToken = jwtService.generateRefreshToken(user.getId());

        RefreshToken newRefresh = RefreshToken.builder()
                .token(refreshToken)
                .expiredAt(Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()))
                .user(user)
                .build();

        repoRefresh.save(newRefresh);

        log.info("Refresh token successfully");

        return AccessTokenDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }


}
