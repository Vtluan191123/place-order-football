package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.service.DateService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/generated")
@RequiredArgsConstructor
public class GeneratedController {
    private final DateService dateService;

    @GetMapping("")
    public void getMethodName() {
        this.dateService.generated();
    }

}
