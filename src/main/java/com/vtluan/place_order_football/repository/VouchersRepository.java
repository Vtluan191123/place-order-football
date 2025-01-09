package com.vtluan.place_order_football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.Vouchers;

@Repository
public interface VouchersRepository extends JpaRepository<Vouchers, Long> {

}
