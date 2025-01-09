package com.vtluan.place_order_football.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.Review;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.TypeField;
import com.vtluan.place_order_football.model.dto.Filter;
import com.vtluan.place_order_football.model.dto.Pagination;

import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.model.dto.response.ResFootballFieldChild;
import com.vtluan.place_order_football.model.dto.response.ResTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.FootballFieldChildAndTimeFrameService;
import com.vtluan.place_order_football.service.FootballFieldService;
import com.vtluan.place_order_football.service.ReviewService;
import com.vtluan.place_order_football.service.TimeFrameService;
import com.vtluan.place_order_football.service.TypeFieldService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/football_field")
@RequiredArgsConstructor
public class FootballFieldController {

    private final FootballFieldService footballFieldService;
    private final TimeFrameService timeFrameService;
    private final TypeFieldService typeFieldService;
    private final ReviewService reviewService;

    private final FootballFieldChildAndTimeFrameService footballFieldChildAndTimeFrameService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<ResFootballField>>> getAllFootField(Filter filter) {

        List<ResFootballField> resFootballFields = new ArrayList<>();

        Page<FootballField> listFootballField = this.footballFieldService.getFootballFieldFilter(filter);

        for (FootballField item : listFootballField.getContent()) {
            resFootballFields.add(this.footballFieldService.footballFieldToResFootballField(item));
        }

        ResponseDto<List<ResFootballField>> responseDto = new ResponseDto();

        Pagination pagination = new Pagination();
        pagination.setTotalPage(listFootballField.getTotalPages());
        responseDto.setPage(pagination);
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(resFootballFields);
        responseDto.setMessenger("Call Api Successful");

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDto<ResFootballField>> getMethodName(@PathVariable("id") long id,
            @RequestParam(value = "star", required = false) Long star) {

        ResFootballField resFootballField = new ResFootballField();
        Optional<FootballField> fOptional = this.footballFieldService.getById(id);
        List<ResFootballFieldChild> lResFootballFieldChilds = new ArrayList<>();
        if (fOptional.isPresent()) {
            Optional<TypeField> typeField = this.typeFieldService
                    .getTypeFieldById(fOptional.get().getTypeField().getId());

            List<FootballFieldChild> lFieldChilds = fOptional.get().getFootballFieldChilds();

            for (FootballFieldChild item : lFieldChilds) {
                ResFootballFieldChild resFootballFieldChild = new ResFootballFieldChild();
                resFootballFieldChild.setId(item.getId());
                resFootballFieldChild.setNameField(item.getNameField());
                List<FootballFieldChildAndTimeFrame> footballFieldChildAndTimeFrames = item
                        .getFootballFieldChildAndTimeFrames();
                List<ResTimeFrame> restimeFrames = new ArrayList<>();

                for (FootballFieldChildAndTimeFrame iChildAndTimeFrame : footballFieldChildAndTimeFrames) {
                    Optional<TimeFrame> timeFrame = this.timeFrameService
                            .getTimeFrameById(iChildAndTimeFrame.getTimeFrame().getId());
                    if (timeFrame.isPresent()) {
                        ResTimeFrame resTimeFrame = new ResTimeFrame();
                        resTimeFrame.setId(timeFrame.get().getId());
                        resTimeFrame.setTimeDes(timeFrame.get().getTimeDes());
                        FootballFieldChildAndTimeFrame item_ = this.footballFieldChildAndTimeFrameService
                                .getByTimeFrameAndFootballFieldChild(timeFrame.get(), item);

                        resTimeFrame.setBooked(item_.getIsBooked());
                        restimeFrames.add(resTimeFrame);
                    }
                }
                resFootballFieldChild.setResTimeFrames(restimeFrames);
                lResFootballFieldChilds.add(resFootballFieldChild);
            }
            List<Review> reviews = new ArrayList<>();
            List<Review> reviewsAll = fOptional.get().getReviews();
            if (star != null) {
                for (Review item : reviewsAll) {

                    if (item.getStars() == star) {
                        reviews.add(item);
                    }

                }
            } else {
                reviews = reviewsAll;
            }

            resFootballField = this.footballFieldService
                    .footballFieldToResFootballField(fOptional.get());
            resFootballField.setResFootballFieldChild(lResFootballFieldChilds);
            resFootballField.setTypeField(typeField.get());
            resFootballField.setResFootballFieldChild(lResFootballFieldChilds);
            resFootballField.setReviews(reviews);
            resFootballField.setTotalReview(reviews.size());
        }

        ResponseDto<ResFootballField> responseDto = new ResponseDto<>();

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(resFootballField);
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/checkTimeExpiration")
    public ResponseEntity<Void> getMethodName() {

        this.footballFieldChildAndTimeFrameService.updateFieldExperation();
        return ResponseEntity.noContent().build();
    }

}
