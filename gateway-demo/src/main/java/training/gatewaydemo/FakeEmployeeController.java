package training.gatewaydemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FakeEmployeeController {

    @GetMapping("/api/employees/fake")
    public Flux<Employee> listEmployees() {
        return Flux.just(
                new Employee(1L, "Fake 1"),
                new Employee(2L, "Fake 2"),
                new Employee(3L, "Fake 3"),
                new Employee(4L, "Fake 4")
        );
    }
}
