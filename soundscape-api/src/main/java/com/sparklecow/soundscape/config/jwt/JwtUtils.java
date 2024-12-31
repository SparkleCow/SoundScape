package com.sparklecow.soundscape.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${application.security.jwt.secret_key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.expiration}")
    private Long EXPIRATION;

    public boolean validateToken(UserDetails userDetails, String token){
        return true;
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, new HashMap<>());
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .signWith(generateSignKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .compact();
    }

    public boolean validateToken(String token, UserDetails User) {
        Jwts.parser().setSigningKey(generateSignKey()).build().parseSignedClaims(token);
        return extractUsername(token).equals(User.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        Date date = extractClaim(token, Claims::getExpiration);
        return date.before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaim(token));
    }

    public Claims extractAllClaim(String token) {
        return Jwts.parser()
                .setSigningKey(generateSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key generateSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
