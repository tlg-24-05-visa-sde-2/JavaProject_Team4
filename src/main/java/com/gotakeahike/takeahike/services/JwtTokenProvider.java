package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.AuthenticationException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private Key secretKey;

    private static JwtTokenProvider instance;

    @PostConstruct
    private void init() {
        // Derive a secure key using PBKDF2
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
            PBEKeySpec keySpec = new PBEKeySpec(new String(decodedKey).toCharArray(), decodedKey, 10000, 512); // Adjust iteration count as needed
            SecretKey generatedSecretKey = keyFactory.generateSecret(keySpec);
            secretKey = new SecretKeySpec(generatedSecretKey.getEncoded(), "HmacSHA512");

            // Set the instance to this instance
            instance = this;
        } catch (Exception e) {
            throw new RuntimeException("Error generating a secure key: " + e.getMessage(), e);
        }
    }

    public static String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + instance.jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(instance.secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public static boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(instance.secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    public static String getUsernameFromToken(String token) throws AuthenticationException {
        Claims claims = Jwts.parser()
                .setSigningKey(instance.secretKey)
                .parseClaimsJws(token)
                .getBody();
        if(claims == null) {
            throw new AuthenticationException("Invalid Token");
        }
        return claims.getSubject();
    }
}