package com.example.authenticationservice.model.mapper;

import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateDTO userCreateDTO);

}
