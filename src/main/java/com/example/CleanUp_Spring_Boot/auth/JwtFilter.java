package com.example.CleanUp_Spring_Boot.auth;

import com.example.CleanUp_Spring_Boot.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
//    private final UserService userService;
    private final String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        logger.info("authorization = " + authorization);

        if(authorization == null || !authorization.startsWith("Bearer ")){
            logger.error("authorization 이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(JwtUtil.isExpired(token, jwtSecretKey)) {
            logger.error("Token이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String userName = "";

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
