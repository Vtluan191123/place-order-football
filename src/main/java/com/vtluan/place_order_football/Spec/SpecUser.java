package com.vtluan.place_order_football.Spec;

import org.springframework.data.jpa.domain.Specification;

import com.vtluan.place_order_football.model.FootballField_;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.Users_;

public class SpecUser {
    public static Specification<Users> searchFieldName(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get(Users_.NAME), "%" + name + "%");
        };
    }
}
