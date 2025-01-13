package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestGoogle {

    @GetMapping("/user")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String name = principal.getAttribute("name");
            String email = principal.getAttribute("email");
            String picture = principal.getAttribute("picture"); // Ảnh đại diện người dùng từ Google
            return "User Info: " + name + ", " + email + ", " + picture;
        }
        return "User not authenticated";
    }
}
