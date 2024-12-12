package com.vtluan.place_order_football.service;

import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtluan.place_order_football.model.CartDetail;
import com.vtluan.place_order_football.repository.CartDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartDetailService {
    private final CartDetailRepository cartDetailRepository;

    public CartDetail createCartDetail(CartDetail cartDetail) {
        return this.cartDetailRepository.save(cartDetail);
    }

    public void deleteCartDetail(CartDetail cartDetail) {
        this.cartDetailRepository.delete(cartDetail);
    }

    public void deleteCartDetailById(long id) {
        this.cartDetailRepository.deleteById(id);
    }

    public Optional<CartDetail> getCartDetailById(long id) {
        return this.cartDetailRepository.findById(id);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    void deleteAllBeforNewDay() {
        this.cartDetailRepository.deleteAll();
    }
}
