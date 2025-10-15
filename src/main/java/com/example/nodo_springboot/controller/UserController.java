package com.example.nodo_springboot.controller;

import com.example.nodo_springboot.dto.UserDTO;
import com.example.nodo_springboot.enums.Status;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    @GetMapping("/profile")
    public UserDTO getUserProfile(@RequestBody String username) {
        System.out.println("Username: " + username);


        return new UserDTO("cao anh", "123456", "a@gmail.com", "Nguyen Manh Cao Anh", new Date(), new Date());
    }

    @PostMapping("/new")
    public String createUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.toString());
        return "User created successfully";
    }
}
