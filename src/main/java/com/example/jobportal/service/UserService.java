package com.example.jobportal.service;

import com.example.jobportal.dto.UserDto;
import com.example.jobportal.entity.User;
import com.example.jobportal.enums.Role;
import com.example.jobportal.exception.DuplicateResourceException;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // ✅ NORMAL USER
        user.setRole(Role.USER);
        
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered");
        }


        return userRepository.save(user);
    }

    public User registerRecruiter(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // ✅ RECRUITER ROLE
        user.setRole(Role.RECRUITER);
        
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already registered");
        }


        return userRepository.save(user);
    }
    
    public void updateResumePath(Long userId, String resumePath) {
        User user = findById(userId);
        user.setResumePath(resumePath);
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }
}
