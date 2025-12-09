package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.ProductService;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ProductController {
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder; 
    private final ProductService productService;

    public ProductController(UploadService uploadService, PasswordEncoder passwordEncoder,
            ProductService productService) {
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct() {
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createUserPage(Model model, @ModelAttribute("newProduct") Product product, @RequestParam("vudoanFile") MultipartFile file )////////////
    {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");

        // vudoan.setAvatar(avatar);

        this.productService.Save(product);
        return "redirect:/admin/user";
    }
    
    
    
}
