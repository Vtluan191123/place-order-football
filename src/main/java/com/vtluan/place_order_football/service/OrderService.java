package com.vtluan.place_order_football.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Cart;
import com.vtluan.place_order_football.model.OrderDetail;
import com.vtluan.place_order_football.model.Orders;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqOrder;
import com.vtluan.place_order_football.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final CartDetailService cartDetailService;
    private final CartService cartService;

    public Orders saveAndUpdate(Orders orders) {
        return this.orderRepository.save(orders);
    }

    public void createOrder(Users user, ReqOrder reqOrder) {
        Cart cart = user.getCart();
        cart.setTotal(0);
        this.cartService.saveOrUpdate(cart);
        Orders orders = new Orders();
        orders.setDate(reqOrder.getDate());
        orders.setUser(user);
        orders = this.saveAndUpdate(orders);

        List<OrderDetail> orderDetails = reqOrder.getOrderDetails();

        for (OrderDetail item : orderDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setFieldChildName(item.getFieldChildName());
            orderDetail.setFieldName(item.getFieldName());
            orderDetail.setOrders(orders);
            orderDetail.setPrice(item.getPrice());
            orderDetail.setTimeFrame(item.getTimeFrame());
            this.orderDetailService.saveAndUpdate(orderDetail);
            this.cartDetailService.deleteCartDetailById(item.getId());
        }

    }

    public List<Orders> getAllOrder() {
        return this.orderRepository.findAll();
    }
}
