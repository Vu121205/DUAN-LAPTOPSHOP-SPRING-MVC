package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.client;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Cart;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.CartDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




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

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long productId = id;
        String email = (String) session.getAttribute("email");
    
        this.productService.handleAddProductToCart(email, productId, session);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for(CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String postDeleteProduct(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;

        this.productService.handleRemoveCartDetail(cartDetailId, session);


        return "redirect:/cart";
    }
    
    
    
    
}
