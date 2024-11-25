package com.vtluan.place_order_football.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.repository.TimeFrameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeFrameService {
    private final TimeFrameRepository timeFrameRepository;

    public Optional<TimeFrame> getTimeFrameById(long id) {
        return this.timeFrameRepository.findById(id);
    }
}
