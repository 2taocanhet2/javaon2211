package com.example.javaonline.service.user;

import com.example.javaonline.dto.Response;
import com.example.javaonline.dto.UserDto;
import com.example.javaonline.entities.UserEntity;

import java.util.List;

public interface UserService {
    Response saveOrUpdate(UserDto userDto);

    List<UserEntity> getAll();

    UserEntity detail(Long id);

    void info();
}
