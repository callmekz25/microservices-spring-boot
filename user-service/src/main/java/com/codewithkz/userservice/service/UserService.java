package com.codewithkz.userservice.service;

import com.codewithkz.userservice.core.exception.DuplicateException;
import com.codewithkz.userservice.core.exception.NotFoundException;
import com.codewithkz.userservice.dto.CreateDto;
import com.codewithkz.userservice.dto.UserDto;
import com.codewithkz.userservice.entity.User;
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

    public UserDto findByEmail(String email) {
        var user = repo.findByEmail(email).orElseThrow(() -> new NotFoundException("Email not found"));

        return mapper.toDto(user);
    }

    public UserDto create(CreateDto dto) {
        var existed = repo.findByEmail(dto.getEmail());

        if (existed.isPresent()) {
            throw new DuplicateException("Email already exists");
        }

        User user = mapper.toEntity(dto);

        repo.save(user);

        return mapper.toDto(user);

    }


}
