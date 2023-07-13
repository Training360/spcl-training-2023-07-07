package training.stormsignal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class StormSignalApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormSignalApplication.class, args);
	}

	@Bean
	public Supplier<Mono<String>> createHelloMessage() {
		return () -> Mono.just("hello world");
	}

	@Bean
	public Consumer<Mono<String>> logHelloMessage() {
		return stringMono -> stringMono.subscribe(s -> log.debug("Message has come: {}", s));
	}

	@Bean
	public Supplier<Mono<String>> download() {
		return () -> WebClient
				.builder()
				.baseUrl("https://katasztrofavedelem.hu/application/uploads/cache/viharjelzo/SWSStations.json")
				.build()
				.get()
				.retrieve()
				.bodyToMono(String.class);
	}

}
