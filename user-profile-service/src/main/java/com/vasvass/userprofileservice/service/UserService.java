package com.vasvass.userprofileservice.service;

import com.vasvass.userprofileservice.model.User;
import com.vasvass.userprofileservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String id, User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setTenantId(userDetails.getTenantId());
            return userRepository.save(user);
        } else {
            // Or throw an exception, or handle gracefully
            return null;
        }
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
