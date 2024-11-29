package com.vtluan.place_order_football.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.dto.Pagination;
import com.vtluan.place_order_football.model.dto.request.ReqFootballField;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.FootballFieldAndTimeFrameService;
import com.vtluan.place_order_football.service.FootballFieldService;
import com.vtluan.place_order_football.service.TimeFrameService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/football_field")
@RequiredArgsConstructor
public class FootballFieldController {

    private final FootballFieldService footballFieldService;
    private final FootballFieldAndTimeFrameService fieldAndTimeFrameService;
    private final TimeFrameService timeFrameService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<ResFootballField>>> getAllFootField(@RequestParam("page") int page) {

        List<ResFootballField> resFootballFields = new ArrayList<>();

        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<FootballField> listFootballField = this.footballFieldService.getAllFootballField(pageable);

        for (FootballField item : listFootballField) {
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

    // @PostMapping("")
    // public ResponseEntity<ResponseDto<ResFootballField>> postCreateFootballFreld(
    // @RequestBody ReqFootballField reqFootballField)
    // throws Exception {
    // if (this.footballFieldService.exitsByName(reqFootballField.getName())) {
    // throw new EmailExists("Name football field already exists");
    // }

    // FootballField footballField =
    // this.footballFieldService.createFootballField(reqFootballField);

    // // tranfer FootballField to ResFootballField
    // ResFootballField resFootballField =
    // this.footballFieldService.reqFootballFieldToResFootballField(footballField,
    // reqFootballField);

    // // create response
    // ResponseDto<ResFootballField> responseDto = new ResponseDto();
    // responseDto.setStatus(HttpStatus.OK.value());
    // responseDto.setError(null);
    // responseDto.setData(resFootballField);
    // responseDto.setMessenger("Create football field Successful");
    // return ResponseEntity.ok().body(responseDto);
    // }

    // @DeleteMapping("{id}")
    // public ResponseEntity<?> deleteFootballField(@PathVariable("id") @NotNull
    // Long id) {
    // Optional<FootballField> footballOptional =
    // this.footballFieldService.getById(id);
    // if (footballOptional.isPresent()) {
    // List<FootballFieldAndTimeFrame> list =
    // footballOptional.get().getFootballFieldAndTimeFrames();
    // for (FootballFieldAndTimeFrame item : list) {
    // this.fieldAndTimeFrameService.deleteItem(item);
    // }
    // this.footballFieldService.deleteFootballFieldById(id);
    // }

    // return ResponseEntity.noContent().build();
    // }

    // @PutMapping("{id}")
    // public ResponseEntity<?> putUpdateFootballField(@PathVariable @NotNull long
    // id,
    // @RequestBody ReqFootballField reqFootballField) {

    // Set<Integer> listIdUpdate = reqFootballField.getTimeframe();

    // Optional<FootballField> footballField =
    // this.footballFieldService.getById(id);
    // if (footballField.isPresent()) {

    // FootballField currenFootballField = footballField.get();
    // currenFootballField.setLocation(reqFootballField.getLocation());
    // currenFootballField.setName(reqFootballField.getName());
    // currenFootballField.setShortDescribe(reqFootballField.getShortDes());
    // currenFootballField.setImage(reqFootballField.getImage());
    // this.footballFieldService.putUpdateFootballField(currenFootballField);

    // List<FootballFieldAndTimeFrame> footballFieldAndTimeFrames =
    // currenFootballField
    // .getFootballFieldAndTimeFrames();
    // for (FootballFieldAndTimeFrame item : footballFieldAndTimeFrames) {
    // this.fieldAndTimeFrameService.deleteItem(item);
    // }

    // for (int item : listIdUpdate) {
    // Optional<TimeFrame> timeFrame = this.timeFrameService
    // .getTimeFrameById(Long.parseLong(String.valueOf(item)));
    // if (timeFrame.isPresent()) {
    // FootballFieldAndTimeFrame fieldAndTimeFrame = new
    // FootballFieldAndTimeFrame();
    // this.fieldAndTimeFrameService.createFootballFieldAndTime(currenFootballField,
    // timeFrame.get());
    // }
    // }

    // }

    // // tranfer FootballField to ResFootballField
    // ResFootballField resFootballField =
    // this.footballFieldService.reqFootballFieldToResFootballField(
    // footballField
    // .get(),
    // reqFootballField);

    // // create response
    // ResponseDto<ResFootballField> responseDto = new ResponseDto();
    // responseDto.setStatus(HttpStatus.OK.value());
    // responseDto.setError(null);
    // responseDto.setData(resFootballField);
    // responseDto.setMessenger("Create football field Successful");
    // return ResponseEntity.ok().body(responseDto);

    // }

}
