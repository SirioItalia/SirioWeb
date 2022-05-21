package com.sirioitalia.api.service;

import com.sirioitalia.api.model.OrderLine;
import com.sirioitalia.api.repository.OrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public Iterable<OrderLine> getOrderLines() {
        return orderLineRepository.findAll();
    }

    public OrderLine getOrderById(Long orderId) {
        return orderLineRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException(
                        "billing with id " + orderId + " does not exists"));
    }

    @Transactional
    public OrderLine createOrderLine(OrderLine orderLineDetails) throws IllegalStateException {
        try {
            return orderLineRepository.save(orderLineDetails);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Transactional
    public void deleteOrderLine(Long orderLineId) throws IllegalStateException {
        try {
            OrderLine orderLineToDelete = orderLineRepository.findById(orderLineId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Order line does not exists"));


            orderLineRepository.delete(orderLineToDelete);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
