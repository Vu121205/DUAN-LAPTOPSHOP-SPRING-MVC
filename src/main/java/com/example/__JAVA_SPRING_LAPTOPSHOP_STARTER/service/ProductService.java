package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Cart;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.CartDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.CartDetailRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.CartRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
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
    
    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                //Tạo mới cart
                Cart orderCart = new Cart();
                orderCart.setUser(user);
                orderCart.setSum(0); 

                cart = this.cartRepository.save(orderCart);
                
            }

            //save cart detail
            //tìm oproduct by id
            Optional<Product> product = this.productRepository.findById(productId);
            if (product.isPresent()) {
                Product realProduct = product.get();

                //check sản phẩm đã từng được thêm vào giỏ hàng chưa
                boolean isExistProductInCart = this.cartDetailRepository.existsByCartAndProduct(cart, realProduct);

                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if(oldDetail == null)
                {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);
                    this.cartDetailRepository.save(cartDetail);

                    
                }else{
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }
                    //update sum in cart
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);

            }

        }

    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }
}
