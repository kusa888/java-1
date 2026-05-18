package com.example.clubmanagement.service;

import com.example.clubmanagement.entity.User;
import com.example.clubmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public User registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("用户名已存在");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, "MEMBER");
        return userRepository.save(user);
    }
    
    public User registerAdmin(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("用户名已存在");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, "ADMIN");
        return userRepository.save(user);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
    }
    
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && user.getEnabled() && passwordEncoder.matches(password, user.getPassword());
    }
    
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
    }
    
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}