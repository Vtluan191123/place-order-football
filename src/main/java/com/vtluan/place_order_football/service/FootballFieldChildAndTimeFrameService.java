package com.vtluan.place_order_football.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TimeFrameService timeFrameService;

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

    public FootballFieldChildAndTimeFrame getByFootFieldChild(FootballFieldChild fieldChild) {
        return this.footballFieldChildAndTimeFrameRepository.findByFootballFieldChild(fieldChild);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetBooked() {
        List<FootballFieldChildAndTimeFrame> footballFieldChildAndTimeFrames = this.footballFieldChildAndTimeFrameRepository
                .findAll();
        for (FootballFieldChildAndTimeFrame item : footballFieldChildAndTimeFrames) {
            item.setIsBooked(false);
            this.generatedFieldAndTimeFrame(item);
        }
    }

    public void updateFieldExperation() {
        LocalTime timeNow = LocalTime.now();
        List<TimeFrame> timeFrames = this.timeFrameService.getAllTimeFramesList();
        for (TimeFrame timeFrame : timeFrames) {
            if (timeNow.isAfter(timeFrame.getEnd())) {
                List<FootballFieldChildAndTimeFrame> fieldChildAndTimeFrames = timeFrame
                        .getFootballFieldAndTimeFrames();
                if (fieldChildAndTimeFrames != null) {
                    for (FootballFieldChildAndTimeFrame item : fieldChildAndTimeFrames) {
                        item.setIsBooked(true);
                        this.footballFieldChildAndTimeFrameRepository.save(item);
                    }
                }

            }
        }

    }

}
