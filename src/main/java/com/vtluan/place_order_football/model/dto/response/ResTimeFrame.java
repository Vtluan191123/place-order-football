package com.vtluan.place_order_football.model.dto.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResTimeFrame {
    long id;
    String timeDes;
    LocalTime begin;
    LocalTime end;
    boolean isBooked;
}
