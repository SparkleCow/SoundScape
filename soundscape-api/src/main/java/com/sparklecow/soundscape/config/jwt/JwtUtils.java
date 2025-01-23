package com.sparklecow.soundscape.config.jwt;

import com.sparklecow.soundscape.exceptions.ExpiredTokenException;
import com.sparklecow.soundscape.exceptions.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusMillis(jwtProperties.getExpiration());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .signWith(generateSignKey())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .compact();
    }

    public boolean validateToken(String token, UserDetails user) throws ExpiredTokenException, InvalidTokenException {
        try{
            Jwts.parser().setSigningKey(generateSignKey()).build().parseSignedClaims(token);

            if (!extractUsername(token).equals(user.getUsername())) {
                throw new InvalidTokenException("Token's username does not match");
            }
            if (isTokenExpired(token)) {
                throw new ExpiredTokenException("Token has expired");
            }
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException("Token has expired: " + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }
    }

    public String extractUsername(String token) throws InvalidTokenException, ExpiredTokenException {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) throws InvalidTokenException, ExpiredTokenException {
        Date date = extractClaim(token, Claims::getExpiration);
        return date.before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws InvalidTokenException, ExpiredTokenException {
        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException("Token is null or empty");
        }
        return claimsResolver.apply(extractAllClaims(token));
    }

    public Claims extractAllClaims(String token) throws ExpiredTokenException, InvalidTokenException {
        try {
            return Jwts.parser()
                    .setSigningKey(generateSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException("The token has expired" + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("The token is invalid: " + e.getMessage());
        }
    }


    private Key generateSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
