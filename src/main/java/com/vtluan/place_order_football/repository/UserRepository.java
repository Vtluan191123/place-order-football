package com.vtluan.place_order_football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users, Long>,
        JpaSpecificationExecutor<FootballField> {
    Boolean existsByEmail(@Param("email") String email);

    Users findByEmail(String email);

    Users findByEmailAndRefreshToken(String email, String rfToken);
}