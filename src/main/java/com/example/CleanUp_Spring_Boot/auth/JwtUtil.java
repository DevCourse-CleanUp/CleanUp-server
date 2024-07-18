package com.example.CleanUp_Spring_Boot.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static String jwtSecretKey;
    private static String jwtExpiredMs;

    @Value("${jwtSecretKey}")
    public void setJwtSecretKey(String jwtSecretKey){
        this.jwtSecretKey = jwtSecretKey;
    }
    @Value("${jwtExpiredMs}")
    public void setJwtExpiredMs(String jwtExpiredMs){
        this.jwtExpiredMs = jwtExpiredMs;
    }

    public static String createJwt(String email){
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(JwtUtil.jwtExpiredMs)))
                .signWith(SignatureAlgorithm.HS256, JwtUtil.jwtSecretKey)
                .compact();
    }

    public static boolean isExpired(String token, String jwtSecretKey){
        System.out.println("토큰 만료 유무 검사 시작");
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String getEmailFromJwt(String jwt){
        String token = jwt.substring(7);
        return (String)Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody().get("email");
    }
}
