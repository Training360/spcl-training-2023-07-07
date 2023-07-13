package training.stormsignal;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import training.stormsignal.dto.Station;

@Component
public class StationMessageConverter extends AbstractMessageConverter {

    public StationMessageConverter() {
        super(new MimeType("application", "station"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Station.class.equals(clazz);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        var payload = message.getPayload();
        return super.convertFromInternal(message, targetClass, conversionHint);
    }

}
