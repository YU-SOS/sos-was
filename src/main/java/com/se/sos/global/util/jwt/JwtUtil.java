package com.se.sos.global.util.jwt;

import com.se.sos.global.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Duration accessTokenDuration;
    private final Duration refreshTokenDuration;
    private final String issuer;

    public JwtUtil(
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.access-expiration}") Duration accessTokenDuration,
        @Value("${jwt.refresh-expiration}") Duration refreshTokenDuration,
        @Value("${jwt.issuer}") String issuer
    ){
        this.secretKey = new SecretKeySpec(secretKey.getBytes(), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenDuration = accessTokenDuration;
        this.refreshTokenDuration = refreshTokenDuration;
        this.issuer = issuer;
    }

    public String generateAccessToken(String userId, String role){
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .issuer(issuer)
                .expiration(createExpire(accessTokenDuration.toMillis()))
                .claim("role", role)
                .claim("tokenType", "access")
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String userId, String role){
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .issuer(issuer)
                .expiration(createExpire(refreshTokenDuration.toMillis()))
                .claim("role", role)
                .claim("tokenType", "refresh")
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String resolveToken(String headerToken){
        return headerToken.substring(7);
    }

    public UUID getIdFromToken(String token){
        Claims claims = parseToken(token);
        return UUID.fromString(claims.getSubject());
    }

    public String getUsername(String token){
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    public String getRole (String token){
        Claims claims = parseToken(token);
        return claims.get("role", String.class);
    }

    public String getTokenType (String token){
        Claims claims = parseToken(token);
        return claims.get("tokenType", String.class);
    }
    public long getRefreshTokenDuration(){
        return refreshTokenDuration.toMillis();
    }
    public long getAccessTokenDuration(){
        return accessTokenDuration.toMillis();
    }
    public Date extractTokenExpirationDate(String token){
        return this.parseToken(token).getExpiration();
    }

    private Date createExpire(Long expiration){
        return new Date(new Date().getTime() + expiration);
    }
}
