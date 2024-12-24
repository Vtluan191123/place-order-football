package com.vtluan.place_order_football.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqChangeInforUser {
    long id;
    String name;
    String phoneNumber;
    MultipartFile file;
}
