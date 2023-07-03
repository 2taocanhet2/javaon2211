package com.example.javaonline.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.javaonline.service.user.UserService;

@Data
@Builder
public class Response {
    int code;
    String message;
    Object data;

    @Autowired
    UserService userService;
}
