package com.example.CleanUp_Spring_Boot.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;

    public static String createJwt(String password, String jwtSecretKey, String jwtExpiredMs){
        return Jwts.builder()
                .claim("password", password)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(jwtExpiredMs)))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public static boolean isExpired(String token, String jwtSecretKey){
        System.out.println("토큰 만료 유무 검사");
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }
}
