package com.codewithkz.orderservice.proxy;


import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceProxy {

    @GetMapping("/api/users/{id}")
    ApiResponse<UserDto> getUserById(@PathVariable("id") Long id);
}
