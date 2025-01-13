package com.vtluan.place_order_football.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vouchers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate begin;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate end;
    long percent;
    String nameEvent;

    @JsonIgnore
    @OneToMany(mappedBy = "vouchers")
    List<FootballField> footballFields;
}
