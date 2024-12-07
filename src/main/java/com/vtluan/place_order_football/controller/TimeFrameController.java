package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.exception.IdInvalidException;
import com.vtluan.place_order_football.exception.TimeInvalidException;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.dto.request.ReqTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.model.dto.response.ResTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.TimeFrameService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/time_frame")
@RequiredArgsConstructor
public class TimeFrameController {

    private final TimeFrameService timeFrameService;

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTimeFrame(@PathVariable("id") Long id) throws IdInvalidException {

        Optional<TimeFrame> timeFrame = this.timeFrameService.getTimeFrameById(id);

        if (timeFrame.isPresent()) {
            this.timeFrameService.deleteTimeFrame(timeFrame.get());
        } else {
            throw new IdInvalidException("id invalid");
        }
        return ResponseEntity.noContent().build();
    }

}
