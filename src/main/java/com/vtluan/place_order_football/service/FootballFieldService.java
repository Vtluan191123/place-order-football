package com.vtluan.place_order_football.service;

import java.security.DrbgParameters.Capability;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ScrollPosition.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vtluan.place_order_football.Spec.SpecFootballField;
import com.vtluan.place_order_football.model.Capacity;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldAndCapacity;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.FootballField_;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.dto.Filter;
import com.vtluan.place_order_football.model.dto.request.ReqFootballField;
import com.vtluan.place_order_football.model.dto.response.ResCapacity;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.repository.FootballFieldRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FootballFieldService {
    private final FootballFieldRepository footballFieldRepository;
    private final TimeFrameService timeFrameService;
    private final FootballFieldChildAndTimeFrameService footballFieldAndTimeFrameService;

    public Page<FootballField> getAllFootballField(Pageable pageable) {
        return this.footballFieldRepository.findAll(pageable);
    }

    public Page<FootballField> getFootballFieldFilter(Filter filter) {

        // Sort
        Sort sort = Sort.unsorted();

        String sortDirection = filter.getSort();
        if (sortDirection != null && "asc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(Sort.Direction.ASC, FootballField_.PRICE);
        } else if (sortDirection != null && "desc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(Sort.Direction.DESC, FootballField_.PRICE);
        }

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        // Filter
        Specification spec = Specification.where(null);

        spec = spec.and(SpecFootballField.searchFieldName(filter.getName()));

        if (filter.getTypeField() != "")
            spec = spec.and(SpecFootballField.matchTypeField(filter.getTypeField()));

        if (filter.getCapacity() != "")
            spec = spec.and(SpecFootballField.matchCapacity(filter.getCapacity()));

        if (filter.getTimeFrame() != "") {

            String[] timeParts = filter.getTimeFrame().split("-");

            List<LocalTime> localTimes = Arrays.stream(timeParts)
                    .map(time -> LocalTime.parse(time)) // Tương đương với LocalTime::parse
                    .collect(Collectors.toList());

            spec = spec.and(SpecFootballField.mathchTimeSlot(localTimes));
        }

        if (filter.getPrice() != "") {

            Specification specPrice = Specification.where(null);
            String[] priceFrames = filter.getPrice().split(",");

            for (int i = 0; i < priceFrames.length; i++) {
                String[] priceFrame = priceFrames[i].split("-");

                List<Double> prices = Arrays.stream(
                        priceFrame)
                        .map(item -> Double.parseDouble(
                                item)) // Tương đương với LocalTime::parse
                        .collect(Collectors.toList());
                specPrice = specPrice.or(SpecFootballField.mathchPrice(prices));

            }

            spec = spec.and(specPrice);

        }

        return this.footballFieldRepository.findAll(spec, pageable);
    }

    public void deleteFootballFieldById(long id) {
        this.footballFieldRepository.deleteById(id);
    }

    public ResFootballField footballFieldToResFootballField(FootballField footballField) {

        // checkfull

        List<Boolean> listStatus = new ArrayList<>();
        List<FootballFieldChild> fieldChilds = footballField.getFootballFieldChilds();
        for (FootballFieldChild fieldChild : fieldChilds) {
            List<FootballFieldChildAndTimeFrame> fieldChildAndTimeFrames = fieldChild
                    .getFootballFieldChildAndTimeFrames();
            for (FootballFieldChildAndTimeFrame fieldChildAndTimeFrame : fieldChildAndTimeFrames) {
                listStatus.add(fieldChildAndTimeFrame.getIsBooked());
            }
        }

        Boolean checkFull = listStatus.stream().allMatch(check -> check == true);

        if (!checkFull) {
            footballField.setStatus(true);
            this.updateFootballField(footballField);
        }

        // get list capacity
        List<ResCapacity> resCapacities = new ArrayList<>();
        List<FootballFieldAndCapacity> fieldAndCapacity = footballField.getFieldAndCapacities();

        for (FootballFieldAndCapacity item : fieldAndCapacity) {
            ResCapacity resCapacity = new ResCapacity();
            Capacity capacity = item.getCapacity();
            resCapacity.setId(capacity.getId());
            resCapacity.setName(capacity.getName());
            resCapacities.add(resCapacity);
        }

        ResFootballField resFootballField = new ResFootballField();

        resFootballField.setId(footballField.getId());
        resFootballField.setImage(footballField.getImage());
        resFootballField.setLocation(footballField.getLocation());
        resFootballField.setName(footballField.getName());
        resFootballField.setPrice(footballField.getPrice());
        resFootballField.setShortDes(footballField.getShortDescribe());
        resFootballField.setTotalField(footballField.getTotalField());
        resFootballField.setStatus(footballField.isStatus());
        resFootballField.setDes(footballField.getDes());
        resFootballField.setTypeField(footballField.getTypeField());
        resFootballField.setResCapacities(resCapacities);

        return resFootballField;
    }

    public Boolean exitsByName(String name) {
        return this.footballFieldRepository.existsByName(name);
    }

    public FootballField createFootballField(ReqFootballField reqFootballField) {
        Set<Integer> listIdTimes = reqFootballField.getTimeframe();

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
        footballField.setShortDescribe(reqFootballField.getShortDes());
        FootballField newFootballField = this.footballFieldRepository.save(footballField);

        List<FootballFieldChildAndTimeFrame> fieldAndTimeFrames = new ArrayList<>();
        // create FootballAndTimeFrame
        for (TimeFrame item : listtTimeFrames) {
            FootballFieldChildAndTimeFrame fieldAndTimeFrame = this.footballFieldAndTimeFrameService
                    .createFootballFieldAndTime(
                            newFootballField, item);

        }

        return newFootballField;
    }

    public void updateFootballField(FootballField field) {
        this.footballFieldRepository.save(field);
    }

    // tranfer reqfootball to resfootball

    // public ResFootballField reqFootballFieldToResFootballField(FootballField
    // footballField,
    // ReqFootballField reqFootballField) {
    // ResFootballField resFootballField = new ResFootballField();
    // resFootballField.setId(footballField.getId());
    // resFootballField.setImage(footballField.getImage());
    // resFootballField.setLocation(footballField.getLocation());
    // resFootballField.setName(footballField.getName());
    // resFootballField.setOrderDetail(footballField.getOrderDetail());
    // resFootballField.setShortDes(footballField.getShortDescribe());
    // Set<Integer> timeFrameId = reqFootballField.getTimeframe();
    // Set<String> timeFrames = new HashSet<>();
    // for (int item : timeFrameId) {
    // Optional<TimeFrame> timeFrame = this.timeFrameService.getTimeFrameById(item);
    // if (timeFrame.isPresent()) {
    // timeFrames.add(timeFrame.get().getTime());
    // }
    // }
    // resFootballField.setTimeframe(timeFrames);
    // return resFootballField;
    // }

    public Optional<FootballField> getById(long id) {
        return this.footballFieldRepository.findById(id);
    }

    public void putUpdateFootballField(FootballField footballField) {
        this.footballFieldRepository.save(footballField);
    }

}