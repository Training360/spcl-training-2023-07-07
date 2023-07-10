package training.configserverdemo;

import lombok.AllArgsConstructor;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ClearCacheController {

    private ApplicationEventPublisher publisher;

    private BusProperties busProperties;

    @PostMapping("/api/caches/clear")
    public void clearCache() {
        publisher.publishEvent(new ClearCachesEvent(this, busProperties.getId(),
                "config-client-demo"));
    }
}
