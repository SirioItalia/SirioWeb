package com.sirioitalia.api.service;

import com.sirioitalia.api.exception.ResourceException;
import com.sirioitalia.api.model.Order;
import com.sirioitalia.api.model.OrderLine;
import com.sirioitalia.api.projection.OrderProjection;
import com.sirioitalia.api.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderLineService orderLineService) {
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
    }


    public Iterable<OrderProjection> getOrders() {
        return orderRepository.findBy();
    }

    public OrderProjection getOrderById(Long orderId) throws ResourceException {
        return orderRepository.findProjectionById(orderId)
                .orElseThrow(() -> new ResourceException(
                        "order does not exists"));
    }

    @Transactional
    public OrderProjection createOrder(Order orderDetails) throws IllegalStateException {
        try {
            orderDetails.setOrderReference(formatOrderReference(orderDetails));
            Order createdOrder = orderRepository.save(orderDetails);
            for (OrderLine orderLine :
                    orderDetails.getOrderLines()) {

                orderLine.setOrder(createdOrder);
                orderLineService.createOrderLine(orderLine);
            }

            return (OrderProjection) createdOrder;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Transactional
    public void deleteOrder(Long orderId) throws IllegalStateException {
        try {
            Order orderToDelete = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Order does not exists"));


            orderRepository.delete(orderToDelete);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private String formatOrderReference(Order orderDetails) {
        Random random = new Random();

        Integer orderYear = LocalDate.now().getYear();
        Integer orderLinesLength = orderDetails.getOrderLines().size();
        Long orderUserId = orderDetails.getUser().getId();
        Integer randomNumber = RandomUtils.nextInt(100, 999);
        String randomChar = RandomStringUtils.randomAlphabetic(1).toUpperCase(Locale.ROOT);

        String formattedOrderReference = String.format(
                "ORD%s%s%s%s%s",
                orderYear,
                orderLinesLength,
                orderUserId,
                randomNumber,
                randomChar
        );

        return formattedOrderReference;
    }
}
