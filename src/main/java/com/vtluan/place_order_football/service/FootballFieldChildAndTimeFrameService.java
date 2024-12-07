package com.vtluan.place_order_football.service;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.repository.FootballFieldChildAndTimeFrameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FootballFieldChildAndTimeFrameService {
    private final FootballFieldChildAndTimeFrameRepository footballFieldChildAndTimeFrameRepository;

    public FootballFieldChildAndTimeFrame createFootballFieldAndTime(FootballField idFbf, TimeFrame idTime) {
        FootballFieldChildAndTimeFrame fieldAndTimeFrame = new FootballFieldChildAndTimeFrame();
        // fieldAndTimeFrame.setFootballField(idFbf);
        fieldAndTimeFrame.setTimeFrame(idTime);
        return this.footballFieldChildAndTimeFrameRepository.save(fieldAndTimeFrame);
    }

    public FootballFieldChildAndTimeFrame getByTimeFrame(TimeFrame timeFrame) {
        return this.footballFieldChildAndTimeFrameRepository.findByTimeFrame(timeFrame);
    }

    public void deleteItem(FootballFieldChildAndTimeFrame item) {
        this.footballFieldChildAndTimeFrameRepository.delete(item);
    }

    // create
    public void generatedFieldAndTimeFrame(FootballFieldChildAndTimeFrame item) {
        this.footballFieldChildAndTimeFrameRepository.save(item);
    }

    public FootballFieldChildAndTimeFrame getByTimeFrameAndFootballFieldChild(TimeFrame timeFrame,
            FootballFieldChild footballFieldChild) {
        return this.footballFieldChildAndTimeFrameRepository.findByAndTimeFrameAndFootballFieldChild(timeFrame,
                footballFieldChild);

    }

}
