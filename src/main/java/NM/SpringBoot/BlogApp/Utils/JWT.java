package NM.SpringBoot.BlogApp.Utils;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

@Component
public class JWT {
    
    @Value("${jwt.secret_key}")
    private String Secret_key = "Default_Secret_Key";

    public String generateToken(Map<String, Object> claims, String Subject){
        String token = Jwts.builder()
                .claims()
                    .subject(Subject)
                .issuedAt(new Date(System.currentTimeMillis())).and()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *10))
                .signWith(Keys.hmacShaKeyFor(Secret_key.getBytes()))
                .compact();

        return token;                        
    }
    @PostConstruct
    public void init() {
        System.out.println("<===========================================================>");
        System.out.println(Secret_key);
    }
}
