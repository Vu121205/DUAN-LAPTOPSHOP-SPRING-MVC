package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Role;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.RoleRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public User getUserById(long id)
    {
        return this.userRepository.findById(id);
    }

    public void DeleteAUser(long id)
    {
        this.userRepository.deleteById(id);;
    }

    public Role getRoleByName(String name)
    {
        return this.roleRepository.findByName(name);
    }

}
