package com.vtluan.place_order_football.model.dto.response;

import java.util.List;

import com.vtluan.place_order_football.model.OrderDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResFootballField {
    long id;
    String name;
    String location;
    String image;

    List<OrderDetail> orderDetail;

    List<String> timeframe;
}
