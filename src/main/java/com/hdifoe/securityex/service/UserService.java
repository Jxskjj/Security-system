package com.hdifoe.securityex.service;

import com.hdifoe.securityex.model.Role;
import com.hdifoe.securityex.model.User;
import com.hdifoe.securityex.repository.UserRepository;
import com.hdifoe.securityex.utils.UserNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    public User register(User user){
        user.setPassword(
                bCryptPasswordEncoder.encode(user.getPassword())
        );
        return userRepository.save(user);
    }

    public String verify(User user)  {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword())
                );
        if (!authentication.isAuthenticated())
            return "fail login";

        return jwtService.generatedToken(user.getUsername());
    }

    public void setAdmin(Integer userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("person not found")
        );

        user.setRole(Role.ADMIN);

        userRepository.save(user);

    }
}
