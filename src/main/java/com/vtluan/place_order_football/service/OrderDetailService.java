package com.vtluan.place_order_football.service;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.OrderDetail;
import com.vtluan.place_order_football.repository.OrderDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail saveAndUpdate(OrderDetail orderDetail) {
        return this.orderDetailRepository.save(orderDetail);
    }
}
