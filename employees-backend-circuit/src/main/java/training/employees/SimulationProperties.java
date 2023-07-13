package training.employees;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "demo.simulation")
@Data
public class SimulationProperties {

    private boolean throwException;

    private Duration delayInDuration;
}
