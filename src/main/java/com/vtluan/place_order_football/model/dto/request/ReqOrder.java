package com.vtluan.place_order_football.model.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.vtluan.place_order_football.model.CartDetail;
import com.vtluan.place_order_football.model.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqOrder {
    String email;
    List<OrderDetail> orderDetails;
    String date;
    String bookerName;
    String bookerPhoneNumber;
    String bookerMessage;
}
