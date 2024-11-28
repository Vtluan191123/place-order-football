package com.vtluan.place_order_football.model.dto.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqFootballField {
    String name;
    String location;
    String image;
    String shortDes;
    Set<Integer> timeframe;
}
