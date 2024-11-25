package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.anotation.ApiResponse;
import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.UserService;

import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<ResponseDto<Users>> postCreateUser(@RequestBody Users user) throws Exception {
        if (this.userService.exitsByEmail(user.getEmail())) {
            throw new EmailExists("Email already exists");
        }
        ResponseDto<Users> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(this.userService.saveOrUpdate(user));
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Users>> getAllUser(@PathVariable("id") long id) {
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
    public ResponseEntity<ResponseDto<User>> deleteUserById(@PathVariable("id") long id) {
        Optional<Users> user = this.userService.getUserById(id);
        if (user.isPresent()) {
            this.userService.deleteUserById(id);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Users>> putUpdateUser(@PathVariable("id") long id, @RequestBody Users user) {

        Optional<Users> curentUser = this.userService.getUserById(id);
        if (curentUser.isPresent()) {
            curentUser.get().setName(user.getName());
            curentUser.get().setPhoneNumber(user.getPhoneNumber());
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

}
