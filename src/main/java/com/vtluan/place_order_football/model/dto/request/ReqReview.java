package com.vtluan.place_order_football.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.Multipart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqReview {
    long userId;
    String nameFootFiled;
    long start;
    String content;
    MultipartFile[] files;

}
