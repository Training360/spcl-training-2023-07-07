package training.employeesfrontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;


@Configuration
@Slf4j
public class ClientConfig {

    @Bean
    public EmployeeClient employeeClient(WebClient.Builder builder) {
        WebClient client = WebClient
                .builder()
                .baseUrl("http://localhost:8081/").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        EmployeeClient service = factory.createClient(EmployeeClient.class);
        return service;
    }

}
