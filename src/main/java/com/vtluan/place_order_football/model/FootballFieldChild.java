package com.vtluan.place_order_football.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class FootballFieldChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String nameField;

    @JsonIgnore
    @OneToMany(mappedBy = "footballFieldChild")
    List<FootballFieldChildAndTimeFrame> footballFieldChildAndTimeFrames;

    @ManyToOne
    @JoinColumn(name = "football_field_id")
    FootballField footballField;
}
