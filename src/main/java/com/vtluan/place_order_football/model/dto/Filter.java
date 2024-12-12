package com.vtluan.place_order_football.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filter {
    String name;
    String typeField;
    String timeFrame;
    String capacity;
    String sort;
    int page;
    int size;
    String price;
}
