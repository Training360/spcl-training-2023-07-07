package training.stormsignal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StormSignalEvent {

    private int deviceId;

    private String name;

    private int level;
}
