package com.hdifoe.securityex.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
public class HomeController {

    @GetMapping("/")
    public String greet(HttpServletRequest http){
        return "welcome to our site. Session id: "+http.getSession().getId();
    }
}
