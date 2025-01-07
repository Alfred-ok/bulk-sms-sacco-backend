package com.example.SwizzSoft_Sms_app.SecurityAndJwt.webtoken;

/*
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;


@Component
public class JwtUtil {

    private static final String SECRET_KEY = "D/Ce9t4s0jPYdmNLfAA4MP+A63FLruFmOI/Wof1VbpArCRBk/0jQHcwgOCWxi2hPcAvDxiz1V7noo0uRY3xkUA==";

    // Generate token with username and role
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSigningKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("role");
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        final String role = extractRole(token);

        // Ensure username and role match
        return username.equals(userDetails.getUsername()) &&
                userDetails.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
*/



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "D/Ce9t4s0jPYdmNLfAA4MP+A63FLruFmOI/Wof1VbpArCRBk/0jQHcwgOCWxi2hPcAvDxiz1V7noo0uRY3xkUA=="; // Replace with your securely generated key

    // Generate token with username only
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Set the username as the subject
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Use HS512 and the secure signing key
                .compact();
    }

    private Key getSigningKey() {
        // You can use the 'Keys.secretKeyFor(SignatureAlgorithm.HS512)' for a more secure key
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Convert your secret key to a byte array and use HMAC
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes()) // Use the same key for validation
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        // Ensure the username from the token matches the input username
        return tokenUsername.equals(username);
    }
}
