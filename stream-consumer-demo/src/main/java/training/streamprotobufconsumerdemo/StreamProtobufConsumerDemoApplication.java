package training.streamprotobufconsumerdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;
import training.dto.EmployeeHasCreatedEvent;

@SpringBootApplication
@EnableSchemaRegistryClient
@Slf4j
public class StreamProtobufConsumerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamProtobufConsumerDemoApplication.class, args);
	}


}
