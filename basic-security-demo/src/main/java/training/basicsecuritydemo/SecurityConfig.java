package training.basicsecuritydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//
//        return new MapReactiveUserDetailsService(user);
//    }

    @Bean
    public UserService userDetailsService(UserRepository userRepository) {
        return new UserService(userRepository, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(spec -> spec
                        // Most mégsem lesz végtelenciklus?
//                                .pathMatchers("/login", "/logout").permitAll()
                                .pathMatchers("/users").hasAnyRole("USER", "ADMIN")
                                .pathMatchers("/users/add").hasRole("ADMIN")
                                .anyExchange().authenticated()
                        )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());
        return http.build();
    }
}
