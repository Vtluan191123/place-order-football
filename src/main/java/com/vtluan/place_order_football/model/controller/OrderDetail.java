package com.vtluan.place_order_football.model.controller;

import java.time.Instant;

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
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "typetime_id")
    TypeTime typeTime;
}
