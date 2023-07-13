package training.employeesfrontend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Controller
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/")
    public Mono<Rendering> index() {
        return Mono.just(Rendering.view("index").build())
                .log();
    }

    @GetMapping("/employees")
    public Mono<Rendering> listEmployees() {
        return Mono.just(
                Rendering.view("employees")
                        .modelAttribute("employees", employeeService.listEmployees())
                        .modelAttribute("command", Mono.just(new CreateEmployeeCommand()))
                        .build()
        );
    }

    @PostMapping("/employees")
    public Mono<Rendering> createUserPost(Mono<CreateEmployeeCommand> command) {
        // Nincs Flash attribute: https://github.com/spring-projects/spring-framework/issues/20575
        return employeeService
                .createEmployee(command).map(user -> Rendering.redirectTo("/employees").build());
    }

}
