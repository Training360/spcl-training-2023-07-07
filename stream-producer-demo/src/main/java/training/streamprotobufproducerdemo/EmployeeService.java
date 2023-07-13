package training.streamprotobufproducerdemo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {

    private StreamBridge streamBridge;

    public void createEmployee(CreateEmployeeCommand command) {


    }

}
