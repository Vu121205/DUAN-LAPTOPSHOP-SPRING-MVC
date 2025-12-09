package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role save(String name);
}
