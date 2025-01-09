package com.vtluan.place_order_football.service;

import java.util.List;

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

    public List<OrderDetail> getByFieldName(String name) {
        return this.orderDetailRepository.findByFieldName(name);
    }
}
