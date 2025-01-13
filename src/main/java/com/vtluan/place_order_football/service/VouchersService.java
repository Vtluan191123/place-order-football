package com.vtluan.place_order_football.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.Spec.SpecUser;
import com.vtluan.place_order_football.Spec.SpecVouchers;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.Vouchers;
import com.vtluan.place_order_football.model.dto.Filter;
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

    public Page<Vouchers> getAllVoucher(Filter filter) {

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());
        Specification<Vouchers> spec = SpecVouchers.searchFieldName(filter.getName() != null ? filter.getName() : "");
        return this.vouchersRepository.findAll(spec, pageable);
    }

}
