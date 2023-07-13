package training.stormsignal;

import kafka.utils.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import training.stormsignal.dto.Station;
import training.stormsignal.dto.StationsDocument;
import training.stormsignal.entity.StormSignalEvent;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class StormSignalApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormSignalApplication.class, args);
	}

	@Bean
	public ConversionServiceFactoryBean conversionServiceFactoryBean(StationToEventConverter stationToEventConverter) {
		var factory = new ConversionServiceFactoryBean();
		factory.setConverters(Set.of(stationToEventConverter));
		return factory;
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
				.bodyToMono(String.class)
				.log();
	}

	@Bean
	public Function<Mono<StationsDocument>, Flux<Station>> convertToStations() {
		return stationsDocumentMono -> stationsDocumentMono
				.flatMapIterable(StationsDocument::getStations);
	}


	@Bean
	Function<Flux<Station>, Flux<Station>> filter() {
		return stationFlux -> stationFlux.filter(station -> station.getGroupId().startsWith("B"));
	}

//	@Bean
//	public Consumer<Flux<Station>> logStations() {
//		return stationFlux -> stationFlux.subscribe(station -> log.debug("Station has come: {}", station));
//	}

 	@Bean
	public Consumer<Flux<StormSignalEvent>> logEvents() {
		return eventFlux -> eventFlux.subscribe(station -> log.debug("Event has come: {}", station));
	}

}
