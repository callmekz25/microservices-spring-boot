package com.codewithkz.authservice.service;

import com.codewithkz.authservice.dto.*;

public interface AuthService {
    UserDto getMe();
    TokenResponseDto login(LoginDto loginDto);
    UserDto register(CreateDto userDto);
    TokenResponseDto refreshToken(RefreshTokenDto refreshTokenDto);

}
