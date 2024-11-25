package com.vtluan.place_order_football.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    Boolean existsByDate(LocalDate date);
}
