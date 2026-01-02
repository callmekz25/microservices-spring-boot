package com.codewithkz.userservice.service;

import com.codewithkz.userservice.dto.UserDto;
import com.codewithkz.userservice.mapper.UserMapper;
import com.codewithkz.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;

    public UserService(UserRepository repo, UserMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<UserDto> findAll() {
        var users = repo.findAll();

        return mapper.toDtoList(users);
    }
}
