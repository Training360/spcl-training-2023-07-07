package training.employeesfrontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Configuration
@Slf4j
public class ClientConfig {

    @Bean
    public EmployeeClient employeeClient(WebClient.Builder builder) {
        WebClient client = WebClient
                .builder()
                .baseUrl("http://localhost:8081/").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        EmployeeClient service = factory.createClient(EmployeeClient.class);
        return service;
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> authorities.stream().flatMap(authority -> {
            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                var realmAccess = (Map<String, Object>) oidcUserAuthority.getAttributes().get("realm_access");
                var roles = (List<String>)realmAccess.get("roles");


//                    OidcIdToken idToken = oidcUserAuthority.getIdToken();
//                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                // Map the claims found in idToken and/or userInfo
                // to one or more GrantedAuthority's and add it to mappedAuthorities
                return roles.stream()
                        .map(roleName -> "ROLE_" + roleName)
                        .map(SimpleGrantedAuthority::new);


            } else if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                // Map the attributes found in userAttributes
                // to one or more GrantedAuthority's and add it to mappedAuthorities
                return Stream.of();
            }
            else if (authority instanceof SimpleGrantedAuthority simpleGrantedAuthority) {
                return Stream.of(simpleGrantedAuthority);
            }
            else {
                throw new IllegalStateException("Invalid authority: %s".formatted(authority.getClass().getName()));
            }
        }).toList();
    }


}
