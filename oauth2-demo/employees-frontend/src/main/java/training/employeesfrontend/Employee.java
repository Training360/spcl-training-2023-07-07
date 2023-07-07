package training.employeesfrontend;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Employee {

    @Id
    private Long id;

    private String name;

    public Employee(String name) {
        this.name = name;
    }

}
