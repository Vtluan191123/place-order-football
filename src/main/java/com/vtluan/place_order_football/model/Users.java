package com.vtluan.place_order_football.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String name;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    @JsonProperty("phone_number")
    String phoneNumber;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String email;

    @NotBlank(message = "Information not empty")
    @Size(min = 3, message = "Minimum 3 characters")
    String password;

    String image;

    @OneToOne(mappedBy = "user")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToMany(mappedBy = "user")
    List<Orders> orders;

}
