package com.example.SwizzSoft_Sms_app.SecurityAndJwt.config;

/*
import com.example.SwizzSoft_Sms_app.Users.AspNetUsers;
import com.example.SwizzSoft_Sms_app.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Assume a JPA repository for users


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AspNetUsers user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Include role in authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                "", // No password
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
*/













import com.example.SwizzSoft_Sms_app.SecurityAndJwt.dto.LoginForm;
import com.example.SwizzSoft_Sms_app.Users.AspNetUsers;
import com.example.SwizzSoft_Sms_app.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // JPA repository for users

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // Find the user based on the username
        AspNetUsers user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Return UserDetails without the role
        return new User(
                user.getUserName(),
                "", // No password (can be used later if needed)
                Collections.emptyList() // No authorities or roles are included
        );
    }
}


