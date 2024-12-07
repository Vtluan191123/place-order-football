package com.vtluan.place_order_football.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "football_field_child_end_time_id")
    FootballFieldChildAndTimeFrame footballFieldChildAndTimeFrame;
}
