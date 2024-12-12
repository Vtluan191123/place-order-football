package com.vtluan.place_order_football.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtluan.place_order_football.model.Cart;
import com.vtluan.place_order_football.model.Users;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(Users user);

}
