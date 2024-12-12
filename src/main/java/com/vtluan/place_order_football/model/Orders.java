package com.vtluan.place_order_football.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String date;
    String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Users user;

    @OneToMany(mappedBy = "orders")
    List<OrderDetail> orderDetails;

    @PrePersist
    void setStatusBeforCreate() {
        this.status = "PENDING";
    }
}
