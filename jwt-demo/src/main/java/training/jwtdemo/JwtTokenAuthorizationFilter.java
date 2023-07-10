package training.jwtdemo;


import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public class JwtTokenAuthorizationFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var authentication = retrieveAuthentication(exchange);
        if (authentication.isPresent()) {
            return chain.filter(exchange).contextWrite(
                    ReactiveSecurityContextHolder.withAuthentication(authentication.get())
            );
        }
        else {
            return chain.filter(exchange);
        }
    }

    private Optional<Authentication> retrieveAuthentication(ServerWebExchange exchange) {
        var cookie = exchange.getRequest().getCookies();
        if (cookie == null) {
            return Optional.empty();
        }
        var cookieValue = (String) cookie.get("token").get(0).getValue();
        if (cookieValue == null) {
            return Optional.empty();
        }

        var token = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(cookieValue)
                .getBody();

        return Optional.of(new UsernamePasswordAuthenticationToken(
                token.getSubject(),
                null,
                ((List<String>) token.get("authorities")).stream().map(SimpleGrantedAuthority::new).toList()
        ));
    }
}
