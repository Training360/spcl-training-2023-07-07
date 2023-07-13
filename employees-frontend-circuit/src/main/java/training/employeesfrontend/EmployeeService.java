package training.employeesfrontend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private EmployeeClient employeeClient;

    private ReactiveCircuitBreaker circuitBreaker;

    public Flux<Employee> listEmployees() {

        return employeeClient.listEmployees()
                .transform(it -> circuitBreaker.run(it, t -> {
                    log.error("error", t);
                    return Flux.just(new Employee(0L, "unknown"));
                }));
    }

    public Mono<Employee> createEmployee(Mono<CreateEmployeeCommand> command) {
        return employeeClient.createEmployee(command);
    }
}
