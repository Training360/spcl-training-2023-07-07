package training.employeesfrontend;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private EmployeeClient employeeClient;

    public Flux<Employee> listEmployees() {

        return employeeClient.listEmployees();
    }

    public Mono<Employee> createEmployee(Mono<CreateEmployeeCommand> command) {
        return employeeClient.createEmployee(command);
    }
}
