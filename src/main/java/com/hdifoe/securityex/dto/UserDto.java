package com.hdifoe.securityex.dto;

import com.hdifoe.securityex.model.Role;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message = "имя не должно быть пустым")
    private String username;

    @NotEmpty(message = "пароль не должен быть пустым")
    private String password;

}
