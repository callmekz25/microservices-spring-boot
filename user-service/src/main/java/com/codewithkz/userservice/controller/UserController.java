package com.codewithkz.userservice.controller;

import com.codewithkz.userservice.dto.UserDto;
import com.codewithkz.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        var users = service.findAll();

        return ResponseEntity.ok(users);
    }
}
