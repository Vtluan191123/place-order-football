package com.vtluan.place_order_football.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUser {

    long id;

    String name;

    String phoneNumber;

    String email;

    String password;

    String image;
    long role;
}
