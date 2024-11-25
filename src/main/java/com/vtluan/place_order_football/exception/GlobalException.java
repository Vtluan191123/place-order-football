package com.vtluan.place_order_football.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = {
            EmailExists.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ResponseDto<?>> handlerException(Exception ex) {

        ResponseDto<Users> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
        responseDto.setError(ex.getMessage());
        responseDto.setData(null);
        responseDto.setMessenger(null);
        return ResponseEntity.badRequest().body(responseDto);
    }
}
