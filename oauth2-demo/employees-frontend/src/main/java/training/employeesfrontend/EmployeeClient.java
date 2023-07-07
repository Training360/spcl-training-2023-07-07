package training.employeesfrontend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("/api/employees")
public interface EmployeeClient {

    @GetExchange
    public Flux<Employee> listEmployees();

    @GetExchange("/{id}")
    public Mono<ResponseEntity<Employee>> findEmployeeById(@PathVariable("id") long id);

    @PostExchange
    public Mono<ResponseEntity<Employee>> createEmployee(@RequestBody Mono<CreateEmployeeCommand> command);

    @PutExchange("/{id}")
    public Mono<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Mono<UpdateEmployeeCommand> command);

    @DeleteExchange("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id);
}
