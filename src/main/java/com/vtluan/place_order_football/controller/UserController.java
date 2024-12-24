package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqChangeInforUser;
import com.vtluan.place_order_football.model.dto.request.ReqUser;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.FileService;
import com.vtluan.place_order_football.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FileService fileService;

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

    @PutMapping("")
    public ResponseEntity<ResponseDto<Users>> putUpdateUser(@ModelAttribute ReqChangeInforUser repChangeInforUser)
            throws IllegalStateException, IOException {
        Optional<Users> userOptional = this.userService.getUserById(repChangeInforUser.getId());
        if (userOptional.isPresent()) {
            String image = "";
            if (repChangeInforUser.getFile() != null && repChangeInforUser.getFile().getSize() > 0) {
                String rootFileToSave = this.fileService.createDirectory("userImages");
                image = this.fileService.upload(rootFileToSave, repChangeInforUser.getFile());
                // delete image old
                Path path = Paths.get(rootFileToSave, userOptional.get().getImage());
                this.fileService.deleteFile(path.toString());
            } else {
                image = userOptional.get().getImage();
            }
            userOptional.get().setImage(image);
            userOptional.get().setPhoneNumber(repChangeInforUser.getPhoneNumber());
            userOptional.get().setName(repChangeInforUser.getName());
            this.userService.setUser(userOptional.get());
        } else {
            throw new UsernameNotFoundException("user not found");
        }
        ResponseDto<Users> responseDto = new ResponseDto();
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(userOptional.get());
        responseDto.setMessenger("Update User Successful");

        return ResponseEntity.ok(responseDto);
    }

}
