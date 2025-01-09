package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.IdInvalidException;

import com.vtluan.place_order_football.model.TimeFrame;

import com.vtluan.place_order_football.service.TimeFrameService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
