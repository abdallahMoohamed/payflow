package com.abdallah.payflow.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.security.KeyPair;


@Service
public class JwtService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final long expiration;

    public JwtService(@Value("${security.jwt.expiration}") long expiration, KeyPair jwtKeyPair) {
        this.expiration = expiration;
        this.publicKey = jwtKeyPair.getPublic();
        this.privateKey = jwtKeyPair.getPrivate();
    }

    // For generate token when login
    public String generateToken(CustomUserDetails customUserDetails) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claim("userId", customUserDetails.getId())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    // Verify token before using AuthenticatedAPI
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Check token validation
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }

}
