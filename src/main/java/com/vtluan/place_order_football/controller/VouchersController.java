package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.model.Vouchers;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.VouchersService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VouchersController {

    private final VouchersService vouchersService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<Vouchers>> postCreateVouches(@RequestBody Vouchers vouchers) {

        Vouchers newVouchers = this.vouchersService.createOrUpdateVouchers(vouchers);

        ResponseDto responseDto = new ResponseDto<>();
        responseDto.setData(newVouchers);
        responseDto.setError(null);
        responseDto.setMessenger("create vouchers Success");
        responseDto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseDto<Vouchers>> putUpdateVouches(@PathVariable("id") Long id,
            @RequestBody Vouchers vouchers) throws Exception {

        Optional<Vouchers> currentVouchers = this.vouchersService.getVouchersById(id);
        if (currentVouchers.isPresent()) {
            currentVouchers.get().setBegin(vouchers.getBegin());
            currentVouchers.get().setEnd(vouchers.getEnd());
            currentVouchers.get().setPercent(vouchers.getPercent());
            this.vouchersService.createOrUpdateVouchers(currentVouchers.get());
        } else {
            throw new Exception("Vouchers not found");
        }

        ResponseDto responseDto = new ResponseDto<>();
        responseDto.setData(currentVouchers.get());
        responseDto.setError(null);
        responseDto.setMessenger("Update vouchers Success");
        responseDto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDto);
    }

}