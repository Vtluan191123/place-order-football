package com.vtluan.place_order_football.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtEncoder jwtEncoder;

    public static MacAlgorithm macAlgorithm = MacAlgorithm.HS256;

    @Value("${vtluan-jwt-screkey}")
    private String jwtKey;

    @Value("${vtluan-jwt-expriration-accesstoken}")
    private long expriration_access;

    @Value("${vtluan-jwt-expriration-refreshtoken}")
    private long expriration_refresh;

    public String createAccessToken(String email, Authentication authentication) {

        Instant now = Instant.now();
        Instant expriration = now.plus(this.expriration_access, ChronoUnit.SECONDS);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .claim("role", authentication)
                .subject(email)
                .expiresAt(expriration)
                .issuedAt(now)
                .build();

        JwsHeader header = JwsHeader.with(macAlgorithm).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }
}
