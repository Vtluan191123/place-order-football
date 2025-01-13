package com.vtluan.place_order_football.Spec;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.vtluan.place_order_football.model.Vouchers;
import com.vtluan.place_order_football.model.Vouchers_;

public class SpecVouchers {
    public static Specification<Vouchers> searchFieldName(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get(Vouchers_.NAME_EVENT), "%" + name + "%");
        };
    }
}
