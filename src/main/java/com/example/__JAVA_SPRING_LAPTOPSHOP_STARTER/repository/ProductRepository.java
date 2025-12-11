package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductById(long id);
    Product deleteById(long id);
}
