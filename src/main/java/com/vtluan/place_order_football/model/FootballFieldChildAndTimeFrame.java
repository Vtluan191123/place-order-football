package com.vtluan.place_order_football.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FootballFieldChildAndTimeFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    @JoinColumn(name = "football_field_child_id")
    FootballFieldChild footballFieldChild;

    Boolean isBooked;
    @ManyToOne
    @JoinColumn(name = "time_frame_id")
    TimeFrame timeFrame;

    @OneToMany(mappedBy = "footballFieldChildAndTimeFrame")
    List<OrderDetail> orderDetails;
}
