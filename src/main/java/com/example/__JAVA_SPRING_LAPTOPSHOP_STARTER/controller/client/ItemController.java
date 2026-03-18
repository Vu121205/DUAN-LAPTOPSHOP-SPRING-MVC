package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Cart;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.CartDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.CartDetailRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.CartRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.ProductService;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ItemController {
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ItemController(ProductService productService, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);

        model.addAttribute("product", product);
        model.addAttribute("id", id);

        List<Product> products = this.productService.findProducts();
        model.addAttribute("products", products);
        
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long productId = id;
        String email = (String) session.getAttribute("email");
    
        this.productService.handleAddProductToCart(email, productId, session, 1);

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

        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String postDeleteProduct(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;

        this.productService.handleRemoveCartDetail(cartDetailId, session);


        return "redirect:/cart";
    }
    
    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart){ 
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);

        return "redirect:/checkout";
    }
    
    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest request) {
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

        return "client/cart/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request, 
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {

        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanksPage(Model model) {
        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(@RequestParam("id") long id,
        @RequestParam("quantity") long quantity, HttpServletRequest request) {
        
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");
            this.productService.handleAddProductToCart(email, id, session, quantity);
        
        return "redirect:/product/" + id;
    }
    
    
    @GetMapping("/products")
    public String getProducts(Model model, 
        @RequestParam("page") Optional<String> pageOptional,
        @RequestParam("name") Optional<String> nameOptional,
        @RequestParam("min-price") Optional<String> minOptional,
        @RequestParam("max-price") Optional<String> maxOptional,
        @RequestParam("factory") Optional<String> factoryOptional,
        @RequestParam("price") Optional<String> priceOptional,
        @RequestParam("sort") Optional<String> sortOptional) {

       int page = 1;

        try {
            if(pageOptional.isPresent())
            {
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 60);

        String name = nameOptional.isPresent() ? nameOptional.get() : "";
        Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, name);

        // case 1
        // double min = minOptional.isPresent() ? Double.parseDouble(minOptional.get()) : 0;
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,  min);

        // case 2
        // double max = maxOptional.isPresent() ? Double.parseDouble(maxOptional.get()) : 0;
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,  max);

        // case 3
        // String factory = factoryOptional.isPresent() ? factoryOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, factory);

        // case 4
        // List<String> factory = Arrays.asList(factoryOptional.get().split(","));
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, factory);

        // case 5
        // String price = priceOptional.isPresent() ? priceOptional.get() : "";
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, price);

        // case 6
        // List<String> price = Arrays.asList(priceOptional.get().split(","));
        // Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, price);


        List<Product> listProducts = prs.getContent();
        model.addAttribute("products", listProducts);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());

        return "client/homepage/products";
    }
    
    
    
}
