package com.codewithkz.userservice.controller;

import com.codewithkz.userservice.core.response.ApiResponse;
import com.codewithkz.userservice.dto.UserDto;
import com.codewithkz.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {
        var users = service.findAll();

        return ResponseEntity.ok(ApiResponse.success(users));
    }

}
