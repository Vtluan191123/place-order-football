package com.vtluan.place_order_football.repository;

import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.TimeFrame;

@Repository
public interface TimeFrameRepository extends JpaRepository<TimeFrame, Long> {
    Boolean existsByBegin(LocalTime begin);

    Boolean existsByEnd(LocalTime end);
}
