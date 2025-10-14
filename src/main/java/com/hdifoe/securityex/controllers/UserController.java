package com.hdifoe.securityex.controllers;

import com.hdifoe.securityex.dto.UserDto;
import com.hdifoe.securityex.model.User;
import com.hdifoe.securityex.service.UserService;
import com.hdifoe.securityex.utils.UserNotCreatedException;
import com.hdifoe.securityex.utils.UserErrorResponse;
import com.hdifoe.securityex.utils.UserValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService, UserValidator userValidator) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/login")
    public String login(@RequestBody  UserDto userDto){
       return userService.verify(convertToUser(userDto));
    }


    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserDto userDto,
                         BindingResult bindingResult){
        userValidator.validate(convertToUser(userDto),bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw  new UserNotCreatedException(errorMsg.toString());
        }
        return convertToUserDto(userService.register(convertToUser(userDto)));
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e){
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private User convertToUser(@Valid UserDto userDto){return modelMapper.map(userDto,User.class);}
    private UserDto convertToUserDto(@Valid User user){return modelMapper.map(user,UserDto.class);}
}
