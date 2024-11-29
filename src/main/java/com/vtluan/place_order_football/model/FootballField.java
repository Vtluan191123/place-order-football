package com.vtluan.place_order_football.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(columnDefinition = "MEDIUMTEXT")
    String des;

    @OneToMany(mappedBy = "footballField")
    List<FootballFieldChild> footballFieldChilds;

    @PrePersist
    @PreUpdate
    @PreRemove
    void autoGeneratedTotalField() {
        if (footballFieldChilds != null) {
            this.totalField = footballFieldChilds.stream().count();
        } else {
            this.totalField = 0;
        }
    }
}
