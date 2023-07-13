package training.streamprotobufproducerdemo;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeService {

    private StreamBridge streamBridge;

    public Mono<CreateEmployeeCommand> createEmployee(Mono<CreateEmployeeCommand> command) {
        return
                command
                        .doOnNext(createEmployeeCommand ->
                                streamBridge.send("employee-has-created-out-0",
                                        new EmployeeHasCreatedEvent(createEmployeeCommand.getName())));
    }

}
