// package com.vtluan.place_order_football.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.vtluan.place_order_football.service.EmailService;

// import jakarta.mail.MessagingException;
// import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// @RestController
// @RequestMapping("/api/v1/mail")
// @RequiredArgsConstructor
// public class EmailController {
// private final EmailService emailService;

// @GetMapping("send-email")
// public String getMethodName() throws MessagingException {
// this.emailService.sendEmail();
// return "ok";
// }

// }
