package com.hdifoe.securityex.controllers;

import com.hdifoe.securityex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/greet")
    public String helloAdmin(){
        return "hello admin";
    }

    @GetMapping("/set_admin/{user_id}")
    public String setAdmin(@PathVariable("user_id") Integer userId){
        userService.setAdmin(userId);
        return "success";
    }

}
