package ru.school.matcha.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.enums.Role;

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
    private final long validityInMillisecondsForPasswordToken;
    private final long validityInMillisecondsForVerifiedToken;

    public JwtTokenProvider() {
        Properties properties;
        try {
            properties = Resources.getResourceAsProperties("application.properties");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        this.secret = Arrays.toString(Base64.getEncoder().encode(properties.getProperty("jwt.token.secret").getBytes()));
        this.validityInMilliseconds = parseLong(properties.getProperty("jwt.token.expired"));
        this.validityInMillisecondsForPasswordToken = parseLong(properties.getProperty("jwt.password.token.expired"));
        this.validityInMillisecondsForVerifiedToken = parseLong(properties.getProperty("jwt.verified.token.expired"));
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

    public String createPasswordToken(String password, Long id) {
        Claims claims = Jwts.claims().setSubject(password);
        claims.put("id", id);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMillisecondsForPasswordToken);
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String createVerifiedToken(Long id) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(id));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMillisecondsForVerifiedToken);
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

    public String getPasswordFromPasswordToken(String passwordToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(passwordToken);
        return claims.getBody().getSubject();
    }

    public long getUserIdFromPasswordToken(String passwordToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(passwordToken);
        return parseLong(claims.getBody().get("id").toString());
    }

    public long getUserIdFromVerifiedToken(String verifiedToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(verifiedToken);
        return parseLong(claims.getBody().getSubject());
    }

    public Long getIdFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return parseLong(claims.getBody().get("id").toString());
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
