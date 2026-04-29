package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Role;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.dto.RegisterDTO;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.OrderRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.ProductRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.RoleRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Page<User> getAllUsers(Pageable pageable)
    {
        return this.userRepository.findAll(pageable);
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

    public User registerDTOtoUser(RegisterDTO registerDTO)
    {
        User user = new User();

        user.setFullName(registerDTO.getFirstName()+" "+registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        return user;
    }

    public boolean checkEmailExist(String email)
    {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String emali)
    {
        return this.userRepository.findByEmail(emali);
    }

    public long countUsers()
    {
        return this.userRepository.count();
    }

    public long countProducts()
    {
        return this.productRepository.count();
    }

    public long countOrders()
    {
        return this.orderRepository.count();
    }


}
