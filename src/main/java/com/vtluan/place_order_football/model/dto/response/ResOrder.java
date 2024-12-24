package com.vtluan.place_order_football.model.dto.response;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vtluan.place_order_football.model.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResOrder {
    String date;
    @JsonFormat(pattern = "HH:mm")
    LocalTime time;
    List<OrderDetail> orderDetails;
}
