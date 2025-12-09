package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product vudoan);


    List<Product> findAll();

    void deleteById(long id);

    Product findById(long id);
}
