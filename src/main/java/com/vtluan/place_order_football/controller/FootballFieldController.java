package com.vtluan.place_order_football.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Array;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldAndTimeFrame;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqFootballField;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.FootballFieldAndTimeFrameService;
import com.vtluan.place_order_football.service.FootballFieldService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/football_field")
@RequiredArgsConstructor
public class FootballFieldController {

    private final FootballFieldService footballFieldService;
    private final FootballFieldAndTimeFrameService fieldAndTimeFrameService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<ResFootballField>>> getAllUser() {
        List<FootballField> listFootballField = this.footballFieldService.getAllFootballField();

        List<ResFootballField> resFootballFields = new ArrayList<>();
        for (FootballField item : listFootballField) {
            resFootballFields.add(this.footballFieldService.footballFieldToResFootballField(item));
        }

        ResponseDto<List<ResFootballField>> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(resFootballFields);
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<ResFootballField>> postCreateUser(@RequestBody ReqFootballField reqFootballField)
            throws Exception {
        if (this.footballFieldService.exitsByName(reqFootballField.getName())) {
            throw new EmailExists("Name football field already exists");
        }

        FootballField footballField = this.footballFieldService.createFootballField(reqFootballField);

        // tranfer FootballField to ResFootballField
        ResFootballField resFootballField = this.footballFieldService.reqFootballFieldToResFootballField(footballField,
                reqFootballField);

        // create response
        ResponseDto<ResFootballField> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(resFootballField);
        responseDto.setMessenger("Create football field Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFootballField(@PathVariable("id") Long id) {
        Optional<FootballField> footballOptional = this.footballFieldService.getById(id);
        if (footballOptional.isPresent()) {
            List<FootballFieldAndTimeFrame> list = footballOptional.get().getFootballFieldAndTimeFrames();
            for (FootballFieldAndTimeFrame item : list) {
                this.fieldAndTimeFrameService.deleteItem(item);
            }
            this.footballFieldService.deleteFootballFieldById(id);
        }

        return ResponseEntity.noContent().build();
    }

}
