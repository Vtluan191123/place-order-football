package com.vtluan.place_order_football.service;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.repository.FootballFieldAndTimeFrameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FootballFieldAndTimeFrameService {
    private final FootballFieldAndTimeFrameRepository fieldAndTimeFrameRepository;

    public FootballFieldChildAndTimeFrame createFootballFieldAndTime(FootballField idFbf, TimeFrame idTime) {
        FootballFieldChildAndTimeFrame fieldAndTimeFrame = new FootballFieldChildAndTimeFrame();
        // fieldAndTimeFrame.setFootballField(idFbf);
        fieldAndTimeFrame.setTimeFrame(idTime);
        return this.fieldAndTimeFrameRepository.save(fieldAndTimeFrame);
    }

    public FootballFieldChildAndTimeFrame getByTimeFrame(TimeFrame timeFrame) {
        return this.fieldAndTimeFrameRepository.findByTimeFrame(timeFrame);
    }

    public void deleteItem(FootballFieldChildAndTimeFrame item) {
        this.fieldAndTimeFrameRepository.delete(item);
    }

    // create
    public void generatedFieldAndTimeFrame(FootballFieldChildAndTimeFrame item) {
        this.fieldAndTimeFrameRepository.save(item);
    }

}
