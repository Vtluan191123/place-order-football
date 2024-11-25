package com.vtluan.place_order_football.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Users> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<Users> getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public Users saveOrUpdate(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public Boolean exitsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
