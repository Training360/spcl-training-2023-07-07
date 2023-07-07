package training.basicsecuritydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
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
        return new UserService(userRepository);
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
                                .pathMatchers("/").authenticated()
                                .pathMatchers("/users").hasRole("ADMIN")
                                .anyExchange().hasAnyRole("USER", "ADMIN")
                        )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());
        return http.build();
    }
}
