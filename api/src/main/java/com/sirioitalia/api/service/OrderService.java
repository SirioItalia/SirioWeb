package com.sirioitalia.api.service;

import com.sirioitalia.api.model.Order;
import com.sirioitalia.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException(
                        "billing with id " + orderId + " does not exists"));
    }

    @Transactional
    public Order createOrder(Order orderDetails) throws IllegalStateException {
        try {
            return orderRepository.save(orderDetails);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Transactional
    public void deleteOrder(Long orderId) throws IllegalStateException {
        try {
            Order orderToDelete = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalStateException(
                            "billing with id " + orderId + " does not exists"));


            orderRepository.delete(orderToDelete);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
