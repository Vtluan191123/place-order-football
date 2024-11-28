package com.vtluan.place_order_football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.FootballFieldAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;

@Repository
public interface FootballFieldAndTimeFrameRepository extends JpaRepository<FootballFieldAndTimeFrame, Long> {
    FootballFieldAndTimeFrame findByTimeFrame(@Param("timeFrame") TimeFrame timeFrame);
}
