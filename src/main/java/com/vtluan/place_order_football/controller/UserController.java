package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqUser;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private String uploadDirectory = "src/main/resources/static/images/";

    @GetMapping("")
    public ResponseEntity<ResponseDto<List<Users>>> getAllUser() {
        List<Users> listUsers = this.userService.getAllUsers();

        ResponseDto<List<Users>> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(listUsers);
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<Users>> postCreateUser(@RequestBody ReqUser reqUser) throws Exception {
        if (this.userService.exitsByEmail(reqUser.getEmail())) {
            throw new EmailExists("Email already exists");
        }

        Users user = this.userService.tranferReqUserToUser(reqUser);

        ResponseDto<Users> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(this.userService.saveOrUpdate(user));
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Users>> getAllUser(@PathVariable("id") @NotNull long id) {
        Optional<Users> user = this.userService.getUserById(id);

        ResponseDto<Users> responseDto = new ResponseDto<>();
        responseDto.setError(null);
        if (user.isPresent()) {
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setData(user.get());
            responseDto.setMessenger("Find Successful with id: " + id);
            return ResponseEntity.ok(responseDto);
        }
        responseDto.setStatus(HttpStatus.NOT_FOUND.value());
        responseDto.setMessenger("User not found");
        responseDto.setData(null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<User>> deleteUserById(@PathVariable("id") @NotNull long id) {
        Optional<Users> user = this.userService.getUserById(id);
        if (user.isPresent()) {
            this.userService.deleteUserById(id);
        } else {
            throw new UsernameNotFoundException("user not found");
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Users>> putUpdateUser(@PathVariable("id") @NotNull long id,
            @RequestBody Users user) throws Exception {

        Optional<Users> curentUser = this.userService.getUserById(id);
        if (curentUser.isPresent()) {
            curentUser.get().setName(user.getName());
            curentUser.get().setEmail(user.getEmail());
            curentUser.get().setPhoneNumber(user.getPhoneNumber());
            curentUser.get().setPassword(user.getPassword());
            this.userService.saveOrUpdate(curentUser.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        // this.userService.createUser(curentUser);

        ResponseDto<Users> responseDto = new ResponseDto<>();
        responseDto.setMessenger("update successful");
        responseDto.setData(curentUser.get());
        responseDto.setError(null);

        return ResponseEntity.ok(responseDto);
    }

    // @GetMapping("/images")
    // public ResponseEntity<byte[]> getImage() throws IOException {
    // // Đọc hình ảnh từ resources
    // ClassPathResource imgFile = new ClassPathResource("/static/images/abc.png");

    // // Trả về hình ảnh dưới dạng byte[]
    // byte[] imageBytes = Files.readAllBytes(imgFile.getFile().toPath());

    // return ResponseEntity.ok()
    // .contentType(MediaType.IMAGE_PNG)
    // .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"abc.png\"")
    // .body(imageBytes);
    // }

}
