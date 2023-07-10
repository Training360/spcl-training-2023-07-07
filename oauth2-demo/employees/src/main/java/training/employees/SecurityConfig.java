package training.employees;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(
//                        spec -> spec.anyExchange().authenticated()
                        spec -> spec.anyExchange().hasRole("employees_user")
                )
                .oauth2ResourceServer(spec -> spec.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public Converter<Jwt,? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        // Convert realm_access.roles claims to granted authorities, for use in access decisions
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return converter;
    }


    @Bean
    public ReactiveJwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties) {
        String issuerUri = properties.getJwt().getIssuerUri();

        var jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
        jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());

        // Use preferred_username from claims as authentication name, instead of UUID subject
        return jwtDecoder;
    }

}
