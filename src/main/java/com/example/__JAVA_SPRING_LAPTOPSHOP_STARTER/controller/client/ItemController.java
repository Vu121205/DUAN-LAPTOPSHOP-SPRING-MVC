package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Cart;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.CartDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product_;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.dto.ProductCriterialDTO;
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
        ProductCriterialDTO productCriterialDTO, HttpServletRequest request) {

       int page = 1;

        try {
            if(productCriterialDTO.getPage().isPresent())
            {
                page = Integer.parseInt(productCriterialDTO.getPage().get());
            } else {
                // page = 1; 
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 3);
        //check sort price
        if (productCriterialDTO.getSort() != null && productCriterialDTO.getSort().isPresent())  
        {
            String sort = productCriterialDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 3, Sort.by(Product_.PRICE).ascending());
            }else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 3, Sort.by(Product_.PRICE).descending());
            }
        }

        Page<Product> prs = this.productService.fetchProductsWithSpec(pageable,  productCriterialDTO);
 
        List<Product> products = prs.getContent().size() > 0 ? prs.getContent() : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + page, "");
            
        }
        model.addAttribute("queryString", qs);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());

        return "client/homepage/products";
    }
    
     
}
