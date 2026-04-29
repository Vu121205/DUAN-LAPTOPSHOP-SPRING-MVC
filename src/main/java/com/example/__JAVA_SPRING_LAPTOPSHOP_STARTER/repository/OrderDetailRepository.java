package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
    
}
