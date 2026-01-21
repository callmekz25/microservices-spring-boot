package com.codewithkz.authservice.service;

import com.codewithkz.authservice.dto.*;

public interface AuthService {
    UserDto getMe();
    AccessTokenDto login(AccountCredential dto);
    UserDto register(CreateDto userDto);
    AccessTokenDto refreshToken(RefreshTokenDto refreshTokenDto);

}
