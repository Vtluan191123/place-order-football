package com.vtluan.place_order_football.model.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TypeTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String timeFrame;
    String price;
}
