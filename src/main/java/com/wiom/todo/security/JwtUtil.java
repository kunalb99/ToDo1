package com.wiom.todo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String privateKey;
    private String publicKey;


    public String generateToken(String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] pkcs8DecodedBytes = Decoders.BASE64.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8DecodedBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws NoSuchAlgorithmException, InvalidKeySpecException {

        final Claims claims = Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }

    public SecretKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] x509DecodedBytes = Decoders.BASE64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(x509DecodedBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return (SecretKey) keyFactory.generatePublic(keySpec);
    }

    public boolean validateToken(String token, UserDetails userDetails) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
