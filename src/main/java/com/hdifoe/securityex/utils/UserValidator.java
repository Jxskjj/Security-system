package com.hdifoe.securityex.utils;

import com.hdifoe.securityex.model.User;
import com.hdifoe.securityex.repository.UserRepository;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent()) {
            errors.rejectValue("Username", "", "Человек с таким именем уже есть.");
        }
    }
}
