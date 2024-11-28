package com.vtluan.place_order_football.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResTimeFrame;
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

    public void deleteTimeFrame(TimeFrame timeFrame) {
        this.timeFrameRepository.delete(timeFrame);
    }

    public void createTimeFrame(TimeFrame timeFrame) {
        this.timeFrameRepository.save(timeFrame);
    }

    public ResTimeFrame timeFrameToResTimeFrame(TimeFrame timeFrame) {
        ResTimeFrame resTimeFrame = new ResTimeFrame(
                timeFrame.getId(),
                timeFrame.getTime(),
                timeFrame.getToday(),
                timeFrame.isBooked());
        return resTimeFrame;
    }

    public Boolean checkTime(String time) {
        return this.timeFrameRepository.existsByTime(time);
    }
}
