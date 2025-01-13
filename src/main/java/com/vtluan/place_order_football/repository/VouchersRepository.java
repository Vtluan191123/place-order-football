package com.vtluan.place_order_football.repository;

import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.Vouchers;

@Repository
public interface VouchersRepository
        extends JpaRepository<Vouchers, Long>, PagingAndSortingRepository<Vouchers, Long>,
        JpaSpecificationExecutor<Vouchers> {

}
