package com.vtluan.place_order_football.exception;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseDto.setData(null);
        responseDto.setMessenger("Token hết hạn hoặc không hợp lệ ");

        objectMapper.writeValue(response.getWriter(), responseDto);

    }

}
