package com.vtluan.place_order_football.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vtluan.place_order_football.model.dto.Pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class ResponseDto<T> {
    int status;
    String error;
    Object messenger;
    T data;
    Pagination page;

}
