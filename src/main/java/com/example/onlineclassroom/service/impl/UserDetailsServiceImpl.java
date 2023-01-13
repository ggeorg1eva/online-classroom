package com.example.onlineclassroom.service.impl;


import com.example.onlineclassroom.model.entity.UserRole;
import com.example.onlineclassroom.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::mapUserToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with username \"" + username + "\" not found!"));
    }

    private UserDetails mapUserToUserDetails(com.example.onlineclassroom.model.entity.User user){

        List<GrantedAuthority> authorities = List.of(this.mapUserRoleToAuthority(user.getUserRole()));

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    private GrantedAuthority mapUserRoleToAuthority(UserRole role){
        return new SimpleGrantedAuthority("ROLE_" + role.getRole().name());
    }
}
