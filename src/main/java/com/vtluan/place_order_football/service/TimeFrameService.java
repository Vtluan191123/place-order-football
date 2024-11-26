package com.vtluan.place_order_football.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Date;
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

    public Set<TimeFrame> getAllTimeFramesSet() {
        return new HashSet<>(this.timeFrameRepository.findAll());
    }

    public List<TimeFrame> getAllTimeFramesList() {
        return this.timeFrameRepository.findAll();
    }

    public TimeFrame getTimeFrameByDate(Date date) {
        return this.timeFrameRepository.findByDate(date);
    }

    public void deleteTimeFrame(TimeFrame timeFrame) {
        this.timeFrameRepository.delete(timeFrame);
    }

    public void createTimeFrame(TimeFrame timeFrame) {
        this.timeFrameRepository.save(timeFrame);
    }
}
