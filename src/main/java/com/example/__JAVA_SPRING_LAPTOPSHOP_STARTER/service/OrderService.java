package com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.Order;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.domain.OrderDetail;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.OrderDetailRepository;
import com.example.__JAVA_SPRING_LAPTOPSHOP_STARTER.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getAllOrder()
    {
        return this.orderRepository.findAll();
    }

    public Optional<Order> fetchOrderById(long id)
    {
        return this.orderRepository.findById(id);
    }

    public void deleteOrderById(long id)
    {
        Optional<Order> orderOptional = this.fetchOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for(OrderDetail orderDetail : orderDetails)
            {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }

        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order)
    {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }


}
