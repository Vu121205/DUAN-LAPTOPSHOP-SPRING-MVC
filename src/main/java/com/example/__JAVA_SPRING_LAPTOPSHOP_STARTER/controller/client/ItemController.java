package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.ProductService;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);

        model.addAttribute("product", product);
        model.addAttribute("id", id);

        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        
        return "client/product/detail";
    }
    
}
