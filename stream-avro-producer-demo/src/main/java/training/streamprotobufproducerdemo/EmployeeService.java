package training.streamprotobufproducerdemo;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import reactor.core.publisher.Mono;
import training.dto.EmployeeHasCreatedEvent;

@Service
@AllArgsConstructor
public class EmployeeService {

    private StreamBridge streamBridge;

    public void createEmployee(CreateEmployeeCommand createEmployeeCommand) {

            streamBridge.send("employee-has-created-out-0",
                        new EmployeeHasCreatedEvent(createEmployeeCommand.getName()));
    }
}
