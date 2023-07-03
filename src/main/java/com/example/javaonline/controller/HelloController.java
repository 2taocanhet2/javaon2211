package com.example.javaonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.javaonline.dto.Response;
import com.example.javaonline.repository.UserRepository;
import com.example.javaonline.service.user.UserService;
import com.example.javaonline.service.user.UserServiceImpl;
import com.example.javaonline.utils.Constant;

@Controller
//@RestController = @Controller + @ResponseBody(return ResponseEntity)
public class HelloController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping("index")
    public String index(){
        userService.info();
        return "index.html";
    }

    @GetMapping("redirect")
//    @ResponseBody
    public ResponseEntity redirect(@RequestParam String key){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "https://google.com");
        return new ResponseEntity(headers, HttpStatus.FOUND);
    }
}
