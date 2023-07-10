package training.jwtdemo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class TokenMain {

    public static void main(String[] args) {
//        var value = Jwts
//                .builder()
//                .setSubject("user")
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
//                .claim("authorities", List.of("USER"))
//                .signWith(SignatureAlgorithm.HS512, "secret".getBytes(StandardCharsets.UTF_8))
//                .compact();

        String value = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjg4OTg2NDUzLCJleHAiOjE2ODg5ODgyNTMsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdfQ.qfnFYw8EhBW7rG-xxSboBN3AqO6WHQNVJWo45HeLpyIUZRMxbWFVzR7k3cTdePb2cTd5C9a-U0iOeOVBbHOg8g";

        var token = Jwts.parser()
                .setSigningKey("secret".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(value)
                .getBody();

        System.out.println(token);
    }
}
