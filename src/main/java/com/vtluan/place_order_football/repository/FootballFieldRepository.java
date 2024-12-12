package com.vtluan.place_order_football.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.FootballField;

@Repository
public interface FootballFieldRepository
        extends JpaRepository<FootballField, Long>,
        PagingAndSortingRepository<FootballField, Long>,
        JpaSpecificationExecutor<FootballField> {
    Boolean existsByName(String name);
}
