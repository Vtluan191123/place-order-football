package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

import com.nimbusds.jose.util.Base64;
import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.Token;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqLogin;
import com.vtluan.place_order_football.model.dto.request.ReqRegister;
import com.vtluan.place_order_football.model.dto.response.ResLogin;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.JwtUtil;
import com.vtluan.place_order_football.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerbBuilder;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<ResponseDto<ResLogin>> login(@RequestBody @Valid ReqLogin loginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerbBuilder.getObject()
                .authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLogin resLogin = this.jwtUtil.handleInforWhenLoginSuccess(loginDto.getEmail());

        String refreshTokenCookie = this.jwtUtil.setRefreshTokenInCookie(resLogin.getToken().getRefreshToken());

        ResponseDto<ResLogin> responseDto = new ResponseDto<>();
        responseDto.setMessenger("login success");
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setData(resLogin);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie).body(responseDto);
    }

    @GetMapping("account")
    public ResponseEntity<ResponseDto<ResLogin.UserLogin>> getInforUserLogin() {
        // String email =
        // SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<String> email = this.jwtUtil.getCurrentUserLogin();
        Users user = this.userService.getUserByEmail(email.get());

        ResLogin.UserLogin userLogin = new ResLogin.UserLogin();
        userLogin.setEmail(user.getEmail());
        userLogin.setId(user.getId());
        userLogin.setName(user.getName());
        userLogin.setTotalCart(user.getCart() != null ? user.getCart().getTotal() : 0);
        userLogin.setPhoneNumber(user.getPhoneNumber());
        userLogin.setImage(user.getImage());

        ResponseDto<ResLogin.UserLogin> responseDto = new ResponseDto<>();
        responseDto.setMessenger("get infor userlogin success");
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setData(userLogin);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("refresh_token")
    public ResponseEntity<ResponseDto<ResLogin>> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "notFound") String refreshToken) throws Exception {

        if (refreshToken.equals("notFound")) {
            throw new Exception("don't get refresh token by cookie");
        }
        // check token
        Jwt decodeRefreshToken = this.jwtUtil.checkDecodeRefreshToken(refreshToken);

        // get email
        String email = decodeRefreshToken.getSubject();

        // check in db has item by refresh and email
        Users user = this.userService.getUserByRefreshTokenAndEmail(refreshToken, email);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorizedaccess");
        }
        // create access token và rf token new
        ResLogin resLogin = this.jwtUtil.handleInforWhenLoginSuccess(user.getEmail());

        String refreshTokenCookie = this.jwtUtil.setRefreshTokenInCookie(resLogin.getToken().getRefreshToken());

        ResponseDto<ResLogin> responseDto = new ResponseDto<>();
        responseDto.setMessenger("refresh token success");
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setData(resLogin);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie).body(responseDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> getLogout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Users user = this.userService.getUserByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorizedaccess");
        }

        user.setRefreshToken(null);
        this.userService.setUser(user);

        String resetRefreshToken = this.jwtUtil.setRefreshTokenWhenLogout();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, resetRefreshToken).build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Users>> postRegisterUser(@RequestBody ReqRegister register) throws EmailExists {

        Users user = this.jwtUtil.resgisterUser(register);

        ResponseDto<Users> responseDto = new ResponseDto<>();
        responseDto.setMessenger("refresh token success");
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setData(user);
        return ResponseEntity.ok().body(responseDto);
    }

}
