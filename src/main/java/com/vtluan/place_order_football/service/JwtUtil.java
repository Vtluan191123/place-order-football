package com.vtluan.place_order_football.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.util.Base64;
import com.vtluan.place_order_football.exception.EmailExists;
import com.vtluan.place_order_football.model.Role;
import com.vtluan.place_order_football.model.Token;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqRegister;
import com.vtluan.place_order_football.model.dto.response.ResLogin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final RoleService roleService;

    public static MacAlgorithm macAlgorithm = MacAlgorithm.HS256;

    @Value("${vtluan-jwt-screkey}")
    private String jwtKey;

    @Value("${vtluan-jwt-expriration-accesstoken}")
    private long expriration_access;

    @Value("${vtluan-jwt-expriration-refreshtoken}")
    private long expriration_refresh;

    public String createAccessToken(String email) {

        Instant now = Instant.now();
        Instant expriration = now.plus(this.expriration_access, ChronoUnit.SECONDS);
        Users user = this.userService.getUserByEmail(email);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .claim("role", user.getRole().getName())
                .subject(email)
                .expiresAt(expriration)
                .issuedAt(now)
                .build();
        JwsHeader header = JwsHeader.with(macAlgorithm).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }

    public String createRefreshToken(String email) {

        Instant now = Instant.now();
        Instant expriration = now.plus(this.expriration_refresh, ChronoUnit.SECONDS);
        Users user = this.userService.getUserByEmail(email);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .claim("role", user.getRole().getName())
                .subject(email)
                .expiresAt(expriration)
                .issuedAt(now)
                .build();

        JwsHeader header = JwsHeader.with(macAlgorithm).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }

    public String setRefreshTokenInCookie(String refreshToken) {
        ResponseCookie setRefreshToken = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expriration_refresh)
                .build();

        return setRefreshToken.toString();
    }

    public String setRefreshTokenWhenLogout() {
        ResponseCookie setRefreshToken = ResponseCookie.from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return setRefreshToken.toString();
    }

    public ResLogin handleInforWhenLoginSuccess(String email) {
        // create token
        String accessToken = this.createAccessToken(email);
        String refreshToken = this.createRefreshToken(email);

        // set ResponseCookie in cookie

        Token objToken = new Token();
        objToken.setAccessToken(accessToken);
        objToken.setRefreshToken(refreshToken);

        Users user = this.userService.getUserByEmail(email);
        user.setRefreshToken(refreshToken);
        this.userService.setUser(user);

        ResLogin resLogin = new ResLogin();
        resLogin.setToken(objToken);

        ResLogin.UserLogin userLogin = new ResLogin.UserLogin();
        if (user != null) {
            userLogin.setEmail(user.getEmail());
            userLogin.setId(user.getId());
            userLogin.setName(user.getName());
            userLogin.setTotalCart(user.getCart() != null ? user.getCart().getTotal() : 0);
            userLogin.setImage(user.getImage());
            resLogin.setUserLogin(userLogin);
        }

        return resLogin;
    }

    public Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private String extractPrincipal(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else if (authentication.getPrincipal() instanceof Jwt jwt) {
                return jwt.getSubject();
            } else if (authentication.getPrincipal() instanceof String s) {
                return s;
            }
        }
        return null;
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(this.jwtKey).decode();
        return new SecretKeySpec(keyBytes, JwtUtil.macAlgorithm.getName());
    }

    public Jwt checkDecodeRefreshToken(String token) {

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(this.macAlgorithm)
                .build();
        try {
            return decoder.decode(token);
        } catch (Exception e) {
            System.out.println(">>>> decode fall" + e.getMessage());
            throw e;
        }

    }

    public Users resgisterUser(ReqRegister register) throws EmailExists {
        boolean checkEmail = this.userService.exitsByEmail(register.getEmail());
        if (checkEmail) {
            throw new EmailExists("email exists !!!");
        }
        Users user = new Users();
        user.setEmail(register.getEmail());
        user.setName(register.getName());
        user.setImage("default_avt.png");
        user.setPhoneNumber(register.getPhoneNumber());
        user.setPassword(register.getPassword());
        Optional<Role> role = this.roleService.getRoleById(register.getRole());
        if (role.isPresent()) {
            user.setRole(role.get());
        }
        user = this.userService.saveOrUpdate(user);
        return user;
    }
}
