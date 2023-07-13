package training.employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public Mono<Employee> createEmployee(Mono<CreateEmployeeCommand> command) {
        return command
                .log("Create employee") // paraméter a category, itt commandot írja ki
                .map(c -> new Employee(c.getName()))
                .flatMap(employeeRepository::save)
                .doOnNext(e -> log.info("Created employee: {}", e))
                .log("Create employee");

    }

    public Flux<Employee> listEmployees() {
        return employeeRepository.findAll();
    }

    public Mono<Employee> findEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    public Mono<Employee> updateEmployee(long id, Mono<UpdateEmployeeCommand> command) {
        return command
                .zipWith(employeeRepository.findById(id))
                .doOnNext(t -> t.getT2().setName(t.getT1().getName()))
                .map(t -> t.getT2())
                .flatMap(e -> employeeRepository.save(e))
                ;
    }


    public Mono<Void> deleteEmployee(long id) {
        return employeeRepository.deleteById(id);
    }

}
