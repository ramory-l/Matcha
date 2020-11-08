package ru.school.matcha.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.security.enums.Role;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import static java.lang.Long.parseLong;

@Slf4j
public class JwtTokenProvider {

    private final String secret;
    private final long validityInMilliseconds;

    public JwtTokenProvider() {
        Properties properties;
        try {
            properties = Resources.getResourceAsProperties("application.properties");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        this.secret = Arrays.toString(Base64.getEncoder().encode(properties.getProperty("jwt.token.secret").getBytes()));
        this.validityInMilliseconds = parseLong(properties.getProperty("jwt.token.expired"));
    }

    public String createToken(Long id, String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", id);
        claims.put("role", role.name());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String resolveToken(String token) {
        if (token != null && token.startsWith("T_")) {
            return token.substring(2);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException ex) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    public String getUsernameFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    public Role getRoleFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        if (claims.getBody().get("role").equals("ADMIN")) {
            return Role.ADMIN;
        } else if (claims.getBody().get("role").equals("USER")) {
            return Role.USER;
        }
        return null;
    }

}
