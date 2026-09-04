package com.prepsprint.service;

import com.prepsprint.dto.RegisterRequestDto;
import com.prepsprint.entity.User;
import com.prepsprint.enums.Role;
import com.prepsprint.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerStudent(RegisterRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail().toLowerCase().trim());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.STUDENT);

        return userRepository.save(user);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email != null ? email.toLowerCase().trim() : "");
    }
}
