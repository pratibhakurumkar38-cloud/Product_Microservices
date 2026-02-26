package com.it;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private String secretKey = null;
    
    
//    In the context of JWT (JSON Web Tokens), claims are simply pieces of information 
//    (key–value pairs) embedded inside the token.
//    They describe the user and token metadata,
//    and are what make JWTs useful beyond just being a random string.

//Registered claims → Standard ones defined by the JWT spec (e.g., iss = issuer, sub = subject, aud = audience, exp = expiration, iat = issued at).
//Public claims → Custom claims meant to be shared publicly across systems (e.g., role, department).
//Private claims → App‑specific claims agreed between parties (e.g., subscription: premium, theme: dark-mode).

//Registered = predefined standards.
//Public = custom but shareable.
//Private = custom and app‑specific.
    
//  {
//	  "sub": "akshay123",        // subject (user ID)
//	  "name": "Akshay",          // custom claim
//	  "role": "USER",            // custom claim
//	  "iat": 1678901234,         // issued at -time
//	  "exp": 1678904834          // expiration -time
//	}    		

    
//    jwt token has 3 parts seperated by dot Header → how to interpret the token.
//Payload → the actual claims (user info, expiry, etc.).
//Signature → proof the token is valid and untampered.
    
    
//Signature=Encrypt(Hash(Base64UrlEncode(header)+"."+Base64UrlEncode(payload))"."+secretKey)


    public String generateToken(User user) {
        Map<String, Object> claims
                = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUserName())
                .issuer("AKSHAY KADAM")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 60*10*1000))
                .and()
                .signWith(generateKey())
                .compact();
    }
    
    

    private SecretKey generateKey() {
        byte[] decode
                = Decoders.BASE64.decode(getSecretKey());

        return Keys.hmacShaKeyFor(decode);
    }


    public String getSecretKey() {
        return secretKey = "yf6b94fcHzp0HzJslp3fJHyISPKJTP1ku6AKofJjt7T";
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
//
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
