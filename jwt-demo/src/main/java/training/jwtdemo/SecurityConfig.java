package training.jwtdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .csrf(spec -> spec.disable())
                .httpBasic(spec -> spec.disable())
                .formLogin(spec -> spec.disable())
                .authorizeExchange(spec ->
                        spec
                                .pathMatchers("/api/login").permitAll()
                                .anyExchange().hasRole("USER"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        var user = User.builder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        var manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService());
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }
}
