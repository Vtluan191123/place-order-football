package com.vtluan.place_order_football.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
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
    double price;
    long totalField;
    String shortDescribe;
    boolean status;

    @ManyToOne
    @JoinColumn(name = "type_field_id")
    TypeField typeField;

    @Column(columnDefinition = "MEDIUMTEXT")
    String des;

    @JsonIgnore
    @OneToMany(mappedBy = "footballField")
    List<FootballFieldChild> footballFieldChilds;

}
