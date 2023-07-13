package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/api/employees")
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(SimulationProperties.class)
public class EmployeeController {

    private EmployeeService employeeService;

    private SimulationProperties simulationProperties;

    @GetMapping
    public Mono<ResponseEntity<Flux<Employee>>> listEmployees(@RequestHeader HttpHeaders headers) {
        log.debug("Call list employees: {}", headers);
        if (simulationProperties.isThrowException()) {
//            throw new IllegalStateException("For simulation purpose throws exception");
            ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            detail.setTitle("Simulation");
            detail.setType(URI.create("error/simulation"));
            detail.setDetail("For simulation purpose throws exception");
            log.debug("Throw exception");
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, detail, new IllegalStateException("For simulation purpose throws exception"));
        }
        if (!simulationProperties.getDelayInDuration().equals(Duration.ZERO)) {
            log.debug("Delay {}", simulationProperties.getDelayInDuration());
            try {
                Thread.sleep(simulationProperties.getDelayInDuration().toMillis());
            } catch (InterruptedException ie) {
                log.error("Not expected", ie);
            }
        }
        return Mono.just(ResponseEntity.ok(employeeService.listEmployees()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Employee>> findEmployeeById(@PathVariable("id") long id) {
        return employeeService.findEmployeeById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Employee>> createEmployee(@RequestBody Mono<CreateEmployeeCommand> command) {
        return employeeService.createEmployee(command)
                .doOnNext(dto -> log.info("return dto {}", dto))
                .map(employee -> ResponseEntity.created(URI.create(String.format("/api/employees/%d", employee.getId()))).body(employee));
    }

    @PutMapping("/{id}")
    public Mono<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Mono<UpdateEmployeeCommand> command) {
        return employeeService.updateEmployee(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
    }
}
