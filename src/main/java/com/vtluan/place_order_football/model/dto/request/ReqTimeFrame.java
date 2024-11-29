package com.vtluan.place_order_football.model.dto.request;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqTimeFrame {
    @JsonFormat(pattern = "HH:mm")
    LocalTime begin;
    @JsonFormat(pattern = "HH:mm")
    LocalTime end;
}
