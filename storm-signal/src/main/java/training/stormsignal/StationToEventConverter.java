package training.stormsignal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import training.stormsignal.dto.Station;
import training.stormsignal.entity.StormSignalEvent;

@Component
public class StationToEventConverter implements Converter<Station, StormSignalEvent> {

    @Override
    public StormSignalEvent convert(Station source) {
        return new StormSignalEvent(source.getHwId(), source.getName(), source.getLevel());
    }
}
