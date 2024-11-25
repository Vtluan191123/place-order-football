package com.vtluan.place_order_football.model;

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
public class FootballFieldAndTimeFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    @JoinColumn(name = "football_field_id")
    FootballField footballField;

    @ManyToOne
    @JoinColumn(name = "time_frame_id")
    TimeFrame timeFrame;
}
