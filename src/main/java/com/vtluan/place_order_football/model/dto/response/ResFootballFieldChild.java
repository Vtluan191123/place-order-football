package com.vtluan.place_order_football.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResFootballFieldChild {
    long id;
    String nameField;
    @JsonProperty("time_frame")
    List<ResTimeFrame> resTimeFrames;
}
