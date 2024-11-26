package com.vtluan.place_order_football.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.Date;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.repository.DateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DateService {
    private final DateRepository dateRepository;
    private final TimeFrameService timeFrameService;
    private final FootballFieldService footballFieldService;
    private final FootballFieldAndTimeFrameService fieldAndTimeFrameService;

    public void generated() {

        // delete after date now
        this.deleteAfterDayNow();
        LocalDate localDate = LocalDate.now();

        // list footField
        List<FootballField> footballFields = this.footballFieldService.getAllFootballField();

        // List timeFrame
        Set<TimeFrame> timeFramesSet = this.timeFrameService.getAllTimeFramesSet();

        // create date and time_frame each date
        for (int i = 0; i < 7; i++) {
            if (!this.dateRepository.existsByDate(localDate.plusDays(i))) {
                Date date = new Date();
                date.setDate(localDate.plusDays(i));
                Date newDate = this.dateRepository.save(date);
                for (TimeFrame item : timeFramesSet) {
                    TimeFrame timeFrame = new TimeFrame();
                    timeFrame.setDate(newDate);
                    timeFrame.setTime(item.getTime());
                    this.timeFrameService.createTimeFrame(timeFrame);
                }
            }
        }
        // list TimeFrame
        List<TimeFrame> timeFrames = this.timeFrameService.getAllTimeFramesList();
        // create football_field_and_time_frame
        for (FootballField footballField : footballFields) {

            List<FootballFieldAndTimeFrame> timeFrameOfFootField = footballField.getFootballFieldAndTimeFrames();
            for (TimeFrame timeFrame : timeFrames) {
                for (FootballFieldAndTimeFrame fieldAndTimeFrame : timeFrameOfFootField) {
                    if (timeFrame.getTime().equals(fieldAndTimeFrame.getTimeFrame().getTime())) {
                        FootballFieldAndTimeFrame newFieldAndTimeFrame = new FootballFieldAndTimeFrame();
                        newFieldAndTimeFrame.setFootballField(footballField);
                        newFieldAndTimeFrame.setTimeFrame(timeFrame);
                        this.fieldAndTimeFrameService.generatedFieldAndTimeFrame(newFieldAndTimeFrame);
                    }
                }
            }

        }
    }

    public void deleteAfterDayNow() {
        List<Date> dates = this.dateRepository.findAll();
        LocalDate now = LocalDate.now();
        for (Date item : dates) {
            if (now.isAfter(item.getDate())) {

                // find and delete timeFrame after now
                TimeFrame timeFrame = this.timeFrameService.getTimeFrameByDate(item);
                this.timeFrameService.deleteTimeFrame(timeFrame);

                FootballFieldAndTimeFrame fieldAndTimeFrame = this.fieldAndTimeFrameService.getByTimeFrame(timeFrame);

                // delete football_and_time_frame
                this.footballFieldService.deleteFootballFieldById(fieldAndTimeFrame.getId());
                // delete time frame
                this.dateRepository.delete(item);
            }
        }
    }
}

/**
 * fix create Football_field
 * ideal : create field football_and_timeframe of football_field by name VD:
 * 6h30 - 8H30;
 */
