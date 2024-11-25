package com.vtluan.place_order_football.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.Orders;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<Orders>>> getAllOrder() {
        List<Orders> listOrders = this.orderService.getAllOrders();

        ResponseDto<List<Orders>> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(listOrders);
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<Orders>> postCreateOrder(@RequestBody Orders order) throws Exception {
        ResponseDto<Orders> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(this.orderService.saveOrUpdate(order));
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Orders>> getAllOrder(@PathVariable("id") long id) {
        Optional<Orders> order = this.orderService.getOrderById(id);

        ResponseDto<Orders> responseDto = new ResponseDto<>();
        responseDto.setError(null);
        if (order.isPresent()) {
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setData(order.get());
            responseDto.setMessenger("Find Successful with id: " + id);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.setStatus(HttpStatus.NOT_FOUND.value());
        responseDto.setMessenger("Order not found");
        responseDto.setData(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Orders>> deleteOrderById(@PathVariable("id") long id) {
        Optional<Orders> order = this.orderService.getOrderById(id);
        if (order.isPresent()) {
            this.orderService.deleteOrderById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Orders>> putUpdateOrder(@PathVariable("id") long id, @RequestBody Orders order) {

        Optional<Orders> curentOrder = this.orderService.getOrderById(id);
        if (curentOrder.isPresent()) {
        }
        // this.orderService.createOrder(curentOrder);

        ResponseDto<Orders> responseDto = new ResponseDto<>();
        responseDto.setMessenger("update successful");
        responseDto.setData(curentOrder.get());
        responseDto.setError(null);

        return ResponseEntity.ok(responseDto);
    }

}
