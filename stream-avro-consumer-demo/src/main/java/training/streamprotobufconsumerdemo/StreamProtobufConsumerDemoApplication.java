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

	@Bean
	public Consumer<Flux<EmployeeHasCreatedEvent>> eventHandler() {
		return employeeFlux -> employeeFlux
				.doOnNext(event -> {if (event.getName().startsWith("X")) {throw new IllegalStateException("erterter");}})
				// Ezzel lecsatlakoztatta a subscriber-t, így ez a hiba jött a köv.
				// üzenetnél: doesn't have subscribers to accept messages
//				.onErrorResume((throwable) -> {log.error("error", throwable); return Mono.empty();})
				.onErrorContinue((t, o) -> log.error("error", t))
				.subscribe(event -> log.debug("Employee has been created: {}", event));
	}

//	@Bean
//	public Function<Flux<EmployeeHasCreatedEvent>, Mono<Void>> eventHandler() {
//		return eventMono -> eventMono
//				.doOnNext(event -> log.debug("Employee has been created: {}", event))
//				.doOnNext(event -> {throw new IllegalStateException("erterter");})
//				.onErrorResume((throwable) -> {log.error("error", throwable); return Mono.empty();})
//				.then();
//	}


}
