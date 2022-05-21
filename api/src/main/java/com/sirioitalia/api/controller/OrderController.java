package com.sirioitalia.api.controller;

import com.sirioitalia.api.model.Order;
import com.sirioitalia.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Iterable<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order foundedOrder = orderService.getOrderById(id);


        return new ResponseEntity<>(foundedOrder, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Order> registerNewBilling(@Valid @RequestBody Order orderDetails) throws IllegalStateException {
        Order createdOrder = orderService.createOrder(orderDetails);


        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }


}
