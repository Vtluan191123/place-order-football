package com.vtluan.place_order_football.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Vouchers;
import com.vtluan.place_order_football.repository.VouchersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VouchersService {
    private final VouchersRepository vouchersRepository;

    public Vouchers createOrUpdateVouchers(Vouchers vouchers) {
        return this.vouchersRepository.save(vouchers);
    }

    public Optional<Vouchers> getVouchersById(long id) {
        return this.vouchersRepository.findById(id);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    void handleExpritionVouchers() {
        List<Vouchers> vouchers = this.vouchersRepository.findAll();

        for (Vouchers item : vouchers) {
            LocalDate now = LocalDate.now();
            if (item.getEnd().isAfter(now)) {
                this.vouchersRepository.delete(item);
            }
        }
    }
}
