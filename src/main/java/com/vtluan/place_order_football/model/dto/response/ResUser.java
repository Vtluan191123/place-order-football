package com.vtluan.place_order_football.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUser {
    long id;
    String email;
    String name;
    String image;
    String phone_number;
    String roleName;
}
