package training.jwtdemo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Controller
@AllArgsConstructor
public class LoginController {

    private ReactiveAuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody Mono<LoginRequest> requestMono) {
        return
            requestMono
                    .flatMap(request -> authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    ))
                    .map(authentication ->
                                    Jwts
                                            .builder()
                                            .setSubject(authentication.getName())
                                            .setIssuedAt(new Date())
                                            .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                                            .claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                                            .signWith(SignatureAlgorithm.HS512, "secret")
                                            .compact()
                            )
                    .map(jwt ->
                            ResponseEntity
                                    .ok()
                                    .header(HttpHeaders.SET_COOKIE, ResponseCookie
                                            .from("token", jwt)
                                            .path("/api")
                                            .maxAge(Duration.of(30, ChronoUnit.MINUTES))
                                            .httpOnly(true)
                                            .build().toString()
                                    )
                                    .body(new LoginResponse("Successful"))
                            );
    }
}
