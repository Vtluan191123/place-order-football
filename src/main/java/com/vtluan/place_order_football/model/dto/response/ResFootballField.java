package com.vtluan.place_order_football.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.Review;
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
    double priceVouchers;
    String shortDes;
    String des;
    long totalField;
    Boolean status;
    TypeField typeField;
    long totalReview;
    String star;
    long totalOrder;

    List<Review> reviews;

    @JsonProperty("list_capacity")
    List<ResCapacity> resCapacities;
    @JsonProperty("list_football_child")
    List<ResFootballFieldChild> resFootballFieldChild;
}
