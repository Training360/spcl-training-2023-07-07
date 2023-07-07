package training.employees;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    private Long id;

    private String name;

    public Employee(String name) {
        this.name = name;
    }

}
