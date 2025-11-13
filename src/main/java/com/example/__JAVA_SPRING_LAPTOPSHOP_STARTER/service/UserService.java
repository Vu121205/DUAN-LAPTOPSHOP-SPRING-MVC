package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersbyEmail(String email)
    {
        return this.userRepository.findOneByEmail(email);
    }

    public User handleSaveUser(User user){
        User vudoan = this.userRepository.save(user);
        System.out.println(vudoan);
        return vudoan;
    }
}
