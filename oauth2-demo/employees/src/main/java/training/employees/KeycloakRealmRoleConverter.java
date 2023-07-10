package training.employees;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRealmRoleConverter

        implements Converter<Jwt, Flux<GrantedAuthority>>
{

    @Override
    public Flux<GrantedAuthority> convert(Jwt source) {
        var realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
        var roles = (List<String>) realmAccess.get("roles");
        return Flux.fromIterable(roles)
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                ;
    }
}
