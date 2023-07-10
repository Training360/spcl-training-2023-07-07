package training.configclientdemo;

import lombok.NoArgsConstructor;
import org.springframework.cloud.bus.event.PathDestinationFactory;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

@NoArgsConstructor
public class ClearCachesEvent extends RemoteApplicationEvent {

    public ClearCachesEvent(Object source, String originService, String destination) {
        super(source, originService, new PathDestinationFactory().getDestination(destination));
    }
}
