package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.model.Token;
import com.vtluan.place_order_football.model.dto.request.ReqLogin;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerbBuilder;

    @PostMapping("login")
    public ResponseEntity<ResponseDto<Token>> login(@RequestBody @Valid ReqLogin loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerbBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);

        // create token
        String token = this.jwtUtil.createAccessToken(loginDto.getEmail(), authentication);
        Token objToken = new Token();
        objToken.setAccessToken(token);

        ResponseDto<Token> responseDto = new ResponseDto<>();
        responseDto.setMessenger("login success");
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setData(objToken);
        return ResponseEntity.ok().body(responseDto);
    }

}
