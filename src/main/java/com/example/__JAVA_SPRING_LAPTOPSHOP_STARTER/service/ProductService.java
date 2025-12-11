package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product)
    {
        return this.productRepository.save(product);
    }

    public List<Product> fetchProducts()
    {
        return this.productRepository.findAll();
    }

    public Product getProductById(long id)
    {
        return this.productRepository.getProductById(id);
    }

    public Product handleSaveProduct(Product currentProduct){
        Product product = this.productRepository.save(currentProduct);
        return product;
    }

    public Product DeleteProductById(long id)
    {
       return this.productRepository.deleteById(id);
    }

}
