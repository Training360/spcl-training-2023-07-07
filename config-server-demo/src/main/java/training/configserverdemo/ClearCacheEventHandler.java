package training.configserverdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClearCacheEventHandler {

    @EventListener
    public void handleEvent(ClearCachesEvent event) {
        log.info("Handle event: {}", event);
    }
}
