package com.codewithkz.orderservice.service.client;


import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceIntegration {

    @GetMapping("/api/users/{id}")
    ApiResponse<UserDto> getUserById(@PathVariable("id") Long id);
}
