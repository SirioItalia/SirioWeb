package com.sirioitalia.api.controller;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Order;
import com.sirioitalia.api.projection.OrderProjection;
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
    public Iterable<OrderProjection> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProjection> getOrderById(@PathVariable Long id) {
        OrderProjection foundedOrder = orderService.getOrderById(id);


        return new ResponseEntity<>(foundedOrder, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<OrderProjection> registerNewBilling(@Valid @RequestBody Order orderDetails) throws IllegalStateException {
        OrderProjection createdOrder = orderService.createOrder(orderDetails);


        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id) throws ResourceException {
        orderService.deleteOrder(id);


        return new ResponseEntity<>(HttpStatus.GONE);
    }


}
