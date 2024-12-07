package com.vtluan.place_order_football.model.dto.request;

import java.util.List;

import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.TimeFrame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqFootballFiledChildAndTimeFrame {
    String email;
    FootballFieldChild footfieldChild;
    List<TimeFrame> timeframes;
}
