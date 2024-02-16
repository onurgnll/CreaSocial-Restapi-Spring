package com.app.facebookclone.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expire.time}")
    private Long expireTime;

    public String generateToken(String email){

        Map<String , Object> claims = new HashMap<>();

        return createToken(claims, email);

    }



    public Claims getClaimsOnToken(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public boolean validateToken(String token, UserDetails userDetails){

        String email = getClaimsOnToken(token).getSubject();
        Date expiration = getClaimsOnToken(token).getExpiration();

        return userDetails.getUsername().equals(email) && expiration.after(new Date());

    }


    private String createToken(Map<String, Object> claims, String email) {

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setSubject(email)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    private Key getKey(){

        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
