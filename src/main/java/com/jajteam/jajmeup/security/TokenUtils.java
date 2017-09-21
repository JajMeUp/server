package com.jajteam.jajmeup.security;

import com.jajteam.jajmeup.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

public class TokenUtils {

    public static final String USER_ROLE = "role";

    public static String extractTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public static Claims parseClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String generateTokenForUser(User user, String secretKey) {
        Instant now = Instant.now();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(USER_ROLE, user.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(Duration.ofDays(7))))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
