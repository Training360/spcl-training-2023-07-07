package training.basicsecuritydemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    public Mono<Rendering> findAll(@AuthenticationPrincipal User user) {
        log.info("user: {}", user);
        return Mono.just(
                Rendering.view("users")
                        .modelAttribute("users", userService.findAll())
                        .modelAttribute("canAdd", user.getRole().equals("ROLE_ADMIN"))
                        .build()
        );
    }

    @GetMapping("/users/add")
    public Mono<Rendering> form() {
        return Mono.just(
                Rendering.view("add")
                        .modelAttribute("command", new CreateUserCommand()).build())
        ;
    }

    @PostMapping("/users/add")
    public Mono<Rendering> post(Mono<CreateUserCommand> commandMono) {
        return userService.create(commandMono)
                .map(user -> Rendering.redirectTo("/users").build());
    }
}
