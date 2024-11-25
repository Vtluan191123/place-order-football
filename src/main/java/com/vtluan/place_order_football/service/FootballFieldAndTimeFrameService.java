package com.vtluan.place_order_football.service;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.repository.FootballFieldAndTimeFrameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FootballFieldAndTimeFrameService {
    private final FootballFieldAndTimeFrameRepository fieldAndTimeFrameRepository;

    public FootballFieldAndTimeFrame createFootballFieldAndTime(FootballField idFbf, TimeFrame idTime) {
        FootballFieldAndTimeFrame fieldAndTimeFrame = new FootballFieldAndTimeFrame();
        fieldAndTimeFrame.setFootballField(idFbf);
        fieldAndTimeFrame.setTimeFrame(idTime);
        return this.fieldAndTimeFrameRepository.save(fieldAndTimeFrame);
    }
}