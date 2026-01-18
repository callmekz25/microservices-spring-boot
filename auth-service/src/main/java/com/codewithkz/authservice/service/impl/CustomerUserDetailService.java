package com.codewithkz.authservice.service.impl;

import com.codewithkz.authservice.entity.User;
import com.codewithkz.authservice.entity.UserPrincipal;
import com.codewithkz.authservice.repository.AuthRepository;
import com.codewithkz.commoncore.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {
    private final AuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new BadRequestException("Invalid email"));

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name()
        );
    }
}
