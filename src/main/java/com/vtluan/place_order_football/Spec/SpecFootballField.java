package com.vtluan.place_order_football.Spec;

import java.security.DrbgParameters.Capability;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.vtluan.place_order_football.model.Capacity;
import com.vtluan.place_order_football.model.Capacity_;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldAndCapacity;
import com.vtluan.place_order_football.model.FootballFieldAndCapacity_;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame_;
import com.vtluan.place_order_football.model.FootballFieldChild_;
import com.vtluan.place_order_football.model.FootballField_;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.TimeFrame_;
import com.vtluan.place_order_football.model.TypeField;
import com.vtluan.place_order_football.model.TypeField_;

import jakarta.persistence.criteria.Join;

public class SpecFootballField {

    public static Specification<FootballField> searchFieldName(String name) {
        return (root, query, builder) -> {
            return builder.like(root.get(FootballField_.NAME), "%" + name + "%");
        };
    }

    public static Specification<FootballField> matchTypeField(String id) {
        return (root, query, builder) -> {
            Join<FootballField, TypeField> typeFieldJoin = root.join(FootballField_.typeField);
            return builder.equal(typeFieldJoin.get(TypeField_.ID), id);
        };
    }

    public static Specification<FootballField> matchCapacity(String id) {
        return (root, query, builder) -> {
            // Join từ FootballField -> FootballFieldAndCapacity
            Join<FootballField, FootballFieldAndCapacity> footballFieldAndCapacityJoin = root
                    .join(FootballField_.FIELD_AND_CAPACITIES);

            // Join từ FootballFieldAndCapacity -> Capacity
            Join<FootballFieldAndCapacity, Capacity> capacityJoin = footballFieldAndCapacityJoin
                    .join(FootballFieldAndCapacity_.CAPACITY);

            // Thêm điều kiện lọc Capacity
            return builder.equal(capacityJoin.get(Capacity_.ID), id);
        };
    }

    public static Specification<FootballField> mathchTimeSlot(List<LocalTime> time) {

        return (root, query, builder) -> {
            Join<FootballField, FootballFieldChild> fieldChildJoin = root.join(FootballField_.FOOTBALL_FIELD_CHILDS);
            Join<FootballFieldChild, FootballFieldChildAndTimeFrame> fieldAndTimeJoin = fieldChildJoin
                    .join(FootballFieldChild_.FOOTBALL_FIELD_CHILD_AND_TIME_FRAMES);
            Join<FootballFieldChildAndTimeFrame, TimeFrame> timeJoin = fieldAndTimeJoin
                    .join(FootballFieldChildAndTimeFrame_.TIME_FRAME);

            return builder.between(timeJoin.get(TimeFrame_.END), time.get(0), time.get(1));
        };

    }

    public static Specification<FootballField> mathchPrice(List<Double> price) {

        return (root, query, builder) -> {

            return builder.between(root.get(FootballField_.price), price.get(0), price.get(1));
        };

    }
}
