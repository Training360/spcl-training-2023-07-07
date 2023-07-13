package training.streamprotobufproducerdemo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("/api/employees")
    public Mono<CreateEmployeeCommand> createEmployee(@RequestBody Mono<CreateEmployeeCommand> commandMono) {
        return employeeService.createEmployee(commandMono);
    }

}
