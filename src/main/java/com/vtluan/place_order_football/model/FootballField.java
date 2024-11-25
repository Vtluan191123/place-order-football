package com.vtluan.place_order_football.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FootballField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String location;
    String image;
    @OneToMany(mappedBy = "footballField")
    List<OrderDetail> orderDetail;

    @OneToMany(mappedBy = "footballField")
    List<FootballFieldAndTimeFrame> footballFieldAndTimeFrames;
}
