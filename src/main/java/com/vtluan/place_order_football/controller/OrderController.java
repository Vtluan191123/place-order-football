package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.model.CartDetail;
import com.vtluan.place_order_football.model.OrderDetail;
import com.vtluan.place_order_football.model.Orders;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqOrder;
import com.vtluan.place_order_football.model.dto.response.ResOrder;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.CartDetailService;
import com.vtluan.place_order_football.service.EmailService;
import com.vtluan.place_order_football.service.OrderDetailService;
import com.vtluan.place_order_football.service.OrderService;
import com.vtluan.place_order_football.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final EmailService emailService;

    @PostMapping("")
    public ResponseEntity<Void> postOrder(@RequestBody ReqOrder reqOrder) throws MessagingException {

        Users user = this.userService.getUserByEmail(reqOrder.getEmail());

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        this.orderService.createOrder(user, reqOrder);
        // place order success -> send email notification
        this.emailService.sendEmail(reqOrder.getEmail(), reqOrder);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<ResOrder>>> getOrder(@RequestParam("id") long id_user) {

        Optional<Users> user = this.userService.getUserById(id_user);
        List<ResOrder> resOrders = new ArrayList<>();
        if (user.isPresent()) {
            List<Orders> orders = user.get().getOrders();
            for (Orders item : orders) {
                ResOrder resOrder = new ResOrder();
                resOrder.setDate(item.getDate());
                resOrder.setTime(item.getTime());
                List<OrderDetail> orderDetails = item.getOrderDetails();
                resOrder.setOrderDetails(orderDetails);
                resOrders.add(resOrder);
            }
        }
        ResponseDto<List<ResOrder>> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(resOrders);
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

}
