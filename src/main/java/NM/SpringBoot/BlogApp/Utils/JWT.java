package NM.SpringBoot.BlogApp.Utils;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JWT {
    
    @Value("${jwt.secret_key}")
    private String Secret_key = "Default_Secret_Key";

    private SecretKey key;

    public String generateToken(String subject, int milliseconds) {

        String token = Jwts.builder()
                .claims() // Returns JWT Claims Builder for Payload Construction
                    .subject(subject)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .and() // Returns Back to JWT Builder
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + milliseconds))
                .compact();

        return token;
    }

    public String generateToken(String subject, int milliseconds, Map<String, Object> claims) {

        String token = Jwts.builder()
                .claims(claims)
                .claims() // Returns JWT Claims Builder for Payload Construction
                    .subject(subject)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .and() // Returns Back to JWT Builder
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + milliseconds))
                .compact();
        return token;                  
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return getUsername(token).equals(username) && !isTokenExpired(token);
    }

    public String getRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    @PostConstruct
    public void init() {
        System.out.println("<===========================================================>");
        System.out.println(Secret_key);
        key = Keys.hmacShaKeyFor(Secret_key.getBytes());
        System.out.println(key.toString());
    }
}
