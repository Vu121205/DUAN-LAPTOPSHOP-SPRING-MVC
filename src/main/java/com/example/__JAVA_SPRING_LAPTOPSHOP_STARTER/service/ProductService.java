package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.ProductRepository;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllUsers()
    {
        return this.productRepository.findAll();
    }

    public Product Save(Product product)
    {
        return this.Save(product);
    }


}
