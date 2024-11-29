package com.vtluan.place_order_football.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    int total;
    String status;
    @OneToOne
    @JoinColumn(name = "user_id")
    Users users;
    @OneToMany(mappedBy = "orders")
    List<OrderDetail> orderDetails;

    @PrePersist
    void defaultStatus() {
        this.status = "PENDING";
    }
}
