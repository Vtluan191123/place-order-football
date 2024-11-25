package com.vtluan.place_order_football.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.dto.request.ReqFootballField;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.repository.FootballFieldRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FootballFieldService {
    private final FootballFieldRepository footballFieldRepository;
    private final TimeFrameService timeFrameService;
    private final FootballFieldAndTimeFrameService footballFieldAndTimeFrameService;

    public List<FootballField> getAllFootballField() {
        return this.footballFieldRepository.findAll();
    }

    public void deleteFootballFieldById(long id) {
        this.footballFieldRepository.deleteById(id);
    }

    public ResFootballField footballFieldToResFootballField(FootballField footballField) {
        ResFootballField resFootballField = new ResFootballField();
        resFootballField.setId(footballField.getId());
        resFootballField.setImage(footballField.getImage());
        resFootballField.setLocation(footballField.getLocation());
        resFootballField.setName(footballField.getName());
        resFootballField.setOrderDetail(footballField.getOrderDetail());
        List<FootballFieldAndTimeFrame> footballFieldAndTimeFrames = footballField.getFootballFieldAndTimeFrames();
        List<String> timeFrame = new ArrayList<>();
        for (FootballFieldAndTimeFrame item : footballFieldAndTimeFrames) {
            timeFrame.add(item.getTimeFrame().getTime());
        }
        resFootballField.setTimeframe(timeFrame);
        return resFootballField;
    }

    public Boolean exitsByName(String name) {
        return this.footballFieldRepository.existsByName(name);
    }

    public FootballField createFootballField(ReqFootballField reqFootballField) {
        List<Integer> listIdTimes = reqFootballField.getTimeframe();

        // find timeframe by id\
        List<TimeFrame> listtTimeFrames = new ArrayList<>();
        for (int item : listIdTimes) {
            Optional<TimeFrame> timeOptional = this.timeFrameService.getTimeFrameById(item);
            if (timeOptional.isPresent()) {
                listtTimeFrames.add(timeOptional.get());
            }
        }

        FootballField footballField = new FootballField();
        footballField.setImage(reqFootballField.getImage());
        footballField.setName(reqFootballField.getName());
        footballField.setLocation(reqFootballField.getLocation());
        FootballField newFootballField = this.footballFieldRepository.save(footballField);

        List<FootballFieldAndTimeFrame> fieldAndTimeFrames = new ArrayList<>();
        // create FootballAndTimeFrame
        for (TimeFrame item : listtTimeFrames) {
            FootballFieldAndTimeFrame fieldAndTimeFrame = this.footballFieldAndTimeFrameService
                    .createFootballFieldAndTime(
                            newFootballField, item);

        }

        return newFootballField;
    }

    // tranfer reqfootball to resfootball

    public ResFootballField reqFootballFieldToResFootballField(FootballField footballField,
            ReqFootballField reqFootballField) {
        ResFootballField resFootballField = new ResFootballField();
        resFootballField.setId(footballField.getId());
        resFootballField.setImage(footballField.getImage());
        resFootballField.setLocation(footballField.getLocation());
        resFootballField.setName(footballField.getName());
        resFootballField.setOrderDetail(footballField.getOrderDetail());
        List<Integer> timeFrameId = reqFootballField.getTimeframe();
        List<String> timeFrames = new ArrayList<>();
        for (int item : timeFrameId) {
            Optional<TimeFrame> timeFrame = this.timeFrameService.getTimeFrameById(item);
            if (timeFrame.isPresent()) {
                timeFrames.add(timeFrame.get().getTime());
            }
        }
        resFootballField.setTimeframe(timeFrames);
        return resFootballField;
    }

    public Optional<FootballField> getById(long id) {
        return this.footballFieldRepository.findById(id);
    }

}