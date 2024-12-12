package com.vtluan.place_order_football.model.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResCart {
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate day;
    long total;

    List<ResCartDetail> resCartDetails;

    @Getter
    @Setter
    public static class ResCartDetail {
        long id;
        String fieldName;
        String fieldChildName;
        String timeFrame;
        double price;
    }
}
