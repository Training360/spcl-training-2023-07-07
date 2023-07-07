package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/employees")
@Slf4j
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping
    public Flux<Employee> listEmployees() {
        return employeeService.listEmployees();
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
