package com.vtluan.place_order_football.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long stars;

    @JsonFormat(pattern = "dd-MM-YYYY")
    LocalDate date;
    String images;
    @Column(columnDefinition = "MEDIUMTEXT")
    String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @ManyToOne
    @JoinColumn(name = "footfield_id")
    FootballField footballField;

    @PrePersist
    void handleBeforCreate() {
        this.date = LocalDate.now();
    }

}
