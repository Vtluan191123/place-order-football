package com.vtluan.place_order_football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;

@Repository
public interface FootballFieldChildAndTimeFrameRepository extends JpaRepository<FootballFieldChildAndTimeFrame, Long> {
    FootballFieldChildAndTimeFrame findByTimeFrame(@Param("timeFrame") TimeFrame timeFrame);

    FootballFieldChildAndTimeFrame findByAndTimeFrameAndFootballFieldChild(TimeFrame timeFrame,
            FootballFieldChild footballFieldChild);

}
