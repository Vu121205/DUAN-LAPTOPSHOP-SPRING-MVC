package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Cart;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.CartDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Order;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.OrderDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Product;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.User;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.CartDetailRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.CartRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.OrderDetailRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.OrderRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if(oldDetail == null)
                {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);
                    this.cartDetailRepository.save(cartDetail);

                    //update sum in cart
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                }else{
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }

    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }
    
    public void handleRemoveCartDetail(long cartDetailId, HttpSession session)
    {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if(cartDetailOptional.isPresent())
        {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartDetail.getCart();

            //delete cart detail
            this.cartDetailRepository.deleteById(cartDetailId);

            //update sum in cart
            if(currentCart.getSum() > 1)
            {
                //update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);

            }else{
                //delete cart
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for(CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress, String receiverPhone) 
    {
        //step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null)
        {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                //create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for(CartDetail cd : cartDetails)
                {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                //create orderDetail
                for (CartDetail cd : cart.getCartDetails()) {
                    OrderDetail od = new OrderDetail();
                    od.setOrder(order);
                    od.setProduct(cd.getProduct());
                    od.setPrice(cd.getPrice());
                    od.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(od);
                }


                // step 2: delete cart and cartDetail
                for (CartDetail cd : cart.getCartDetails())
                {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                //update sum
                session.setAttribute("sum", 0);
            }
            
        } 
    }
}
