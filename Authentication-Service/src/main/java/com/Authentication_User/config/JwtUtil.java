package com.Authentication_User.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";                               // holds the secret key

    @Value("${jwt.expiration}")
    private long jwtExpiration;                             // holds the expiration time


    public String extractUsername(String token){                        // This method extracts the username from the token.
        return
                extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token,
                              Function<Claims,T> claimsResolver){
        final Claims claims =
                extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities());
        return createToken(claims,userDetails.getUsername());
    }

    public String createToken(Map<String,Object> claims, String subject ){
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 10))// 10 hours
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY.getBytes())
                .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails){                         // This is use to validate the token
       final String username = extractUsername(token);
       return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration)
                .before(new Date());
    }
}
