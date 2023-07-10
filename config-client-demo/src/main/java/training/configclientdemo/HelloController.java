package training.configclientdemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@EnableConfigurationProperties(DemoProperties.class)
@AllArgsConstructor
public class HelloController {

    private DemoProperties demoProperties;

    @GetMapping("/api/hello")
    public Mono<Message> sayHello(@RequestParam String name) {
        log.debug("say hello");

        return Mono.just(new Message(demoProperties.getMessage().formatted(name)));
    }
}
