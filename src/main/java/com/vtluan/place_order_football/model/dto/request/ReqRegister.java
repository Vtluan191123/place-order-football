package com.vtluan.place_order_football.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqRegister {
    String email;
    String name;
    String phoneNumber;
    String password;
    long role = 2;
}
