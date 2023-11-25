package com.hackathon.hackathon.global.jwt;

import com.hackathon.hackathon.global.exception.ExpiredTokenException;
import com.hackathon.hackathon.global.exception.InvalidSignatureTokenException;
import com.hackathon.hackathon.global.exception.InvalidTokenException;
import com.hackathon.hackathon.global.exception.NotMatchedTokenTypeException;
import com.hackathon.hackathon.global.jwt.dto.DecodedJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.hackathon.hackathon.global.constant.StaticValue.ACCESS_TOKEN;
import static com.hackathon.hackathon.global.constant.StaticValue.REFRESH_TOKEN;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtProperties jwtProperties;

    public Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    public Jws<Claims> getClaim(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (SignatureException e) {
            throw new InvalidSignatureTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e){
            throw new InvalidTokenException();
        }
    }

    private String issueToken(Long userId, String role, String type){
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("hackathon")
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTokenExp()))
                .claim("type",type)
                .claim("role", role)
                .signWith(getSecretKey())
                .compact();
    }

    public String generateAccessToken(Long userId, String role){
        return issueToken(userId,role,ACCESS_TOKEN);
    }
    public String generateRefreshToken(Long userId, String role){
        return issueToken(userId,role,REFRESH_TOKEN);
    }

    public DecodedJwtToken decodeToken(String token, String type){
        Claims claims = getClaim(token).getBody();
        checkType(claims,type);
        DecodedJwtToken result = DecodedJwtToken.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(String.valueOf(claims.get("role")))
                .type(String.valueOf(claims.get("type")))
                .build();
        return result;
    }
    private void checkType(Claims claims, String type){
        if(type.equals(String.valueOf(claims.get("type")))) return;
        else throw new NotMatchedTokenTypeException();
    }
}