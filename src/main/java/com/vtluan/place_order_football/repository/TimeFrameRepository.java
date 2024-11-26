package com.vtluan.place_order_football.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.Date;
import com.vtluan.place_order_football.model.TimeFrame;

@Repository
public interface TimeFrameRepository extends JpaRepository<TimeFrame, Long> {
    TimeFrame findByDate(Date date);
}
