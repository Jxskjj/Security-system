package com.hdifoe.securityex.service;

import com.hdifoe.securityex.model.UserPrincipial;
import com.hdifoe.securityex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonalUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipial(
                userRepository.findByUsername(username).orElseThrow(
                        () -> new UsernameNotFoundException("person not found"))
        );
    }
}
