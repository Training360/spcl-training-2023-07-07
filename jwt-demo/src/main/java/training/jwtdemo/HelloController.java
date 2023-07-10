package training.jwtdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Mono<HelloResponse> sayHello() {
        return Mono.just(new HelloResponse("Hello JWT!"));
    }
}
