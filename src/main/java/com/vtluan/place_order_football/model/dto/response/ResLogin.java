package com.vtluan.place_order_football.model.dto.response;

import com.vtluan.place_order_football.model.Token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResLogin {
    Token token;
    UserLogin UserLogin;

    @Getter
    @Setter
    public static class UserLogin {
        long id;
        String email;
        String name;
        long totalCart;
        String image;
    }
}
