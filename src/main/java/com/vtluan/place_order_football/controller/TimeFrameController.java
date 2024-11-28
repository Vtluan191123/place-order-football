package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.exception.IdInvalidException;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.dto.request.ReqTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.model.dto.response.ResTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.TimeFrameService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/time_frame")
@RequiredArgsConstructor
public class TimeFrameController {

    private final TimeFrameService timeFrameService;

    @GetMapping("/generated")
    public void getUpdateToday() {
        List<TimeFrame> timeFrames = this.timeFrameService.getAllTimeFramesList();

        for (TimeFrame item : timeFrames) {
            item.setToday(LocalDate.now());
            item.setBooked(false);
            this.timeFrameService.createTimeFrame(item);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<ResTimeFrame>>> getAllTimeFrame() {
        List<TimeFrame> timeFrames = this.timeFrameService.getAllTimeFramesList();

        List<ResTimeFrame> list = new ArrayList<>();

        for (TimeFrame timeFrame : timeFrames) {
            ResTimeFrame item = this.timeFrameService.timeFrameToResTimeFrame(timeFrame);
            list.add(item);
        }

        ResponseDto<List<ResTimeFrame>> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(list);
        responseDto.setMessenger("Call api successful!!!");

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResTimeFrame>> postCreateTimeFrame(@RequestBody ReqTimeFrame reqTimeFrame)
            throws EmailExists {
        if (this.timeFrameService.checkTime(reqTimeFrame.getTime())) {
            throw new EmailExists("Time already exists !!");
        }
        TimeFrame timeFrame = new TimeFrame();
        timeFrame.setTime(reqTimeFrame.getTime());
        this.timeFrameService.createTimeFrame(timeFrame);

        ResTimeFrame item = this.timeFrameService.timeFrameToResTimeFrame(timeFrame);
        ResponseDto<ResTimeFrame> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(item);
        responseDto.setMessenger("Call api successful!!!");

        return ResponseEntity.ok().body(responseDto);
    }

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
