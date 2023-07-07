package training.basicsecuritydemo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    public Mono<Rendering> findAll() {
        return Mono.just(
                Rendering.view("users")
                        .modelAttribute("users", userService.findAll())
                        .build()
        );
    }
}
