package com.vtluan.place_order_football.model.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String name;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String phoneNumber;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String email;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String password;

    @OneToOne(mappedBy = "user")
    Orders orders;

}
