package ru.school.matcha.security.jwt;

import io.jsonwebtoken.*;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.exceptions.MatchaException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import static java.lang.Long.parseLong;

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

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
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

}
