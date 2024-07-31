package com.gotakeahike.takeahike.services;

import com.gotakeahike.takeahike.models.userDetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.AuthenticationException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    private static Key secretKey;

    private static JwtTokenProvider instance;

    @PostConstruct
    private void init() {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
            PBEKeySpec keySpec = new PBEKeySpec(new String(decodedKey).toCharArray(), decodedKey, 10000, 512);
            SecretKey generatedSecretKey = keyFactory.generateSecret(keySpec);
            secretKey = new SecretKeySpec(generatedSecretKey.getEncoded(), "HmacSHA512");
            instance = this;
        } catch (Exception e) {
            throw new RuntimeException("Error generating a secure key: " + e.getMessage(), e);
        }
    }

    public static String generateToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + instance.jwtExpirationInMs);
        System.out.println("UserId: " + userDetails.getUserId());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", userDetails.getUserId())  // Ensure this is getting the correct user ID
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public static boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    public static Claims extractClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            logger.info("Claims: {}", claims);
            return claims;
        } catch (Exception e) {
            logger.error("Error extracting claims from token: ", e);
            return null;
        }
    }

    public static Long extractUserId(String token) {
        Claims claims = extractClaims(token);
        System.out.println("Token in claims: " + token);
        if (claims != null && claims.get("userId") != null) {
            logger.info("Extracted userId: {}", claims.get("userId"));
            return Long.parseLong(claims.get("userId").toString());
        } else {
            logger.error("UserId claim is missing or null");
            return null;
        }
    }
}