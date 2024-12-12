package com.vtluan.place_order_football.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReqLogin {
    @NotBlank(message = "email không được để chống")
    String email;
    @NotBlank(message = "password không được để chống")
    String password;
}
