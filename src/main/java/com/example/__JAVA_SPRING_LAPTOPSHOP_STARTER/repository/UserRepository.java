package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;

//crud: create, read, update, delete
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User vudoan);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    void deleteById(long id);

    User findById(long id);

    boolean existsByEmail(String email);
}
