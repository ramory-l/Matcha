package ru.school.matcha.security.jwt;

import io.jsonwebtoken.*;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.exceptions.MatchaException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

        this.secret = properties.getProperty("jwt.token.secret");
        this.validityInMilliseconds = parseLong(properties.getProperty("jwt.token.expired"));
    }

    // passwordEncoder

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

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("T_")) {
            return bearerToken.substring(2, bearerToken.length());
         }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // JwtAuthenticationException
            throw new MatchaException("JWT token is expired or invalid");
        }
    }
}
