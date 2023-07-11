package training.jwtdemo;


import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
public class JwtTokenAuthorizationFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono
                .justOrEmpty(exchange.getRequest().getCookies())
                .filter(e -> !exchange.getRequest().getPath().toString().startsWith("/api/login"))
                .flatMapIterable(Map::entrySet)
                .filter(entry -> entry.getKey().equals("token"))
                .map(Map.Entry::getValue)
                .flatMapIterable(list -> list)
                .next()
                .map(HttpCookie::getValue)
                .map(token -> Jwts.parser()
                        .setSigningKey("secret")
                        .parseClaimsJws(token)
                        .getBody())
                .map(claims ->
                                new UsernamePasswordAuthenticationToken(
                                        claims.getSubject(),
                                        null,
                                        ((List<String>) claims.get("authorities")).stream().map(SimpleGrantedAuthority::new).toList()
                                )
                        )
                // Ötletet merítve innnen: AuthenticationWebFilter
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .flatMap(authentication -> chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
    }


}
