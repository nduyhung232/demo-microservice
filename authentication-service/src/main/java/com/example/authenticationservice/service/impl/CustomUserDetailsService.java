//package com.example.authenticationservice.service.impl;
//
//import com.example.authenticationservice.model.entity.User;
//import com.example.authenticationservice.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
////        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
////                .map(SimpleGrantedAuthority::new)
////                .collect(Collectors.toList());
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(), user.getPassword(), user.getIsActive(), true, true, true, authorities
//        );
//    }
//}
