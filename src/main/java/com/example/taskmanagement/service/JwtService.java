package com.example.taskmanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public <T> T extractClaims(String token, Function<Claims, T> function){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
//        log.info("Subject: {}", claims.getSubject());
//        log.info("Expiration: {}", claims.getExpiration());
//        log.info("Token: {}", token);
        return function.apply(claims);
    }

    public boolean isTokenValid(String email, String token){
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}