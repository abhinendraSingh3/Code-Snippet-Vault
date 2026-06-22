package com.personalProject.codeVault.securityConfig;

import com.personalProject.codeVault.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKeyString;//extracting from the properties
    private SecretKey getSecretKey(){
    return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    //generate jwt token
    public String generateJwt(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email",user.getEmail())
                .claim("id",user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims verifySignatureAndExtractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //extract username
    public String extractUserName(String token){
        return verifySignatureAndExtractAllClaims(token).getSubject();
    }

    //extract emailId
    public String extractMailId(String token){
        return verifySignatureAndExtractAllClaims(token).get("email",String.class);
    }

    //extract userId
    public Long extractUserId(String token) {
        return verifySignatureAndExtractAllClaims(token).get("id",Long.class);
    }
    //extract expiration
    public Date getExpiration(String token) {
        return verifySignatureAndExtractAllClaims(token).getExpiration();
    }

    //is token expired
    public Boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token){
        try{
            verifySignatureAndExtractAllClaims(token);
            return !isTokenExpired(token);
        }
        catch(Exception e){
            return false;
        }
    }





}
