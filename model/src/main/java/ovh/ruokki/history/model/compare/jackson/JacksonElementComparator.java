package ovh.ruokki.history.model.compare.jackson;

import java.util.List;

import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Event;
import ovh.ruokki.history.model.compare.Comparator;

@Slf4j
public class JacksonElementComparator implements Comparator<JsonElement>, JacksonComparator {

    private final List<JacksonComparator> jacksonComparators;

    public JacksonElementComparator(List<JacksonComparator> jacksonComparators) {
        this.jacksonComparators = jacksonComparators;
    }

    @Override
    public Event compare(JsonElement before, JsonElement after) {
        Event event = Event.empty();
        this.compare("root", before, after, event);
        return event;
    }

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        jacksonComparators.stream()
                .filter((comparator) -> comparator.canCompare(before, after))
                .findFirst()
                .ifPresentOrElse(comparator -> {
                    log.debug("Use {} to compare {}", comparator, property);
                    comparator.compare(property, before, after, event);
                }, () -> {
                    log.warn("Property {}: ignored", property);
                });

    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {
        return true;
    }

}
