package com.vtluan.place_order_football.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Date;
import com.vtluan.place_order_football.repository.DateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DateService {
    private final DateRepository dateRepository;

    public void generatedSevenDay() {
        LocalDate localDate = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            if (!this.dateRepository.existsByDate(localDate.plusDays(i))) {
                Date date = new Date();
                date.setDate(localDate.plusDays(i));
                this.dateRepository.save(date);
            }
        }
    }
}
