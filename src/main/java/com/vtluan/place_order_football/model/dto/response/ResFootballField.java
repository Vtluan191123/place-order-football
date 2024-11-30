package com.vtluan.place_order_football.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.TypeField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResFootballField {
    long id;
    String name;
    String location;
    String image;
    double price;
    String shortDes;
    long totalField;
    Boolean status;
    TypeField typeField;
    @JsonProperty("list_football_child")
    List<ResFootballFieldChild> resFootballFieldChild;
}
