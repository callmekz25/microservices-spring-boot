package com.codewithkz.userservice.mapper;


import com.codewithkz.userservice.dto.CreateDto;
import com.codewithkz.userservice.dto.UserDto;
import com.codewithkz.userservice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(CreateDto userDto);
    List<UserDto> toDtoList(List<User> users);
}
