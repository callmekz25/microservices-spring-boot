package com.codewithkz.authservice.mapper;


import com.codewithkz.authservice.dto.CreateDto;
import com.codewithkz.authservice.dto.UserDto;
import com.codewithkz.authservice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(CreateDto userDto);
    List<UserDto> toDtoList(List<User> users);
}
