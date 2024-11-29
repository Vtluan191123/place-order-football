package com.vtluan.place_order_football.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResFootballField {
    long id;
    String name;
    String location;
    String image;
    double price;
    String shortDes;
    long totalField;
    Boolean status;
}
