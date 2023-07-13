package training.streamprotobufconsumerdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@Slf4j
public class StreamProtobufConsumerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamProtobufConsumerDemoApplication.class, args);
	}

	@Bean
	public Function<Flux<EmployeeHasCreatedEvent>, Flux<ReplyEvent>> handleEvent() {
		return
				eventFlux -> eventFlux
						.doOnNext(event -> log.debug("Event has come: {}", event))
						.onErrorContinue((t, o) -> log.error("Error", t))
//						.subscribe();
						.map(changedEvent -> new ReplyEvent("Hello %s".formatted(changedEvent.getName())));
	}
}
