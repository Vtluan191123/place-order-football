package com.vtluan.place_order_football.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Orders;
import com.vtluan.place_order_football.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Orders> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Optional<Orders> getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public Orders saveOrUpdate(Orders order) {

        return this.orderRepository.save(order);
    }

    public void deleteOrderById(long id) {
        this.orderRepository.deleteById(id);
    }
}
