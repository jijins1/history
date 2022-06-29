package ovh.ruokki.history.model.change;

import java.util.ArrayList;
import java.util.List;

public record Event(List<Change> changes,
        List<Creation> creations,
        List<Deletion> deletion,
        List<Event> subEvents) {

    public static Event empty() {
        return new Event(new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    };

}
