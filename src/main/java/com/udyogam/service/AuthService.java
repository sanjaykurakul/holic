package com.udyogam.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.udyogam.entity.User;
import com.udyogam.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;

    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);

    }
//    if(repo.findByEmail(user.getEmail()).isPresent()){
//        throw new RuntimeException("Email already exists");
//    }
}