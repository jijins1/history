package ovh.ruokki.history.model.compare.jackson;

import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Event;

@Slf4j
public class JacksonComparatorCreationObject implements JacksonComparator {

    private JacksonElementComparator jacksonComparator;

    public JacksonComparatorCreationObject(JacksonElementComparator jacksonComparator) {
        this.jacksonComparator = jacksonComparator;
    }

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        log.debug("Creation object property {}", property);
        Event subEvent = Event.empty();
        after.getAsJsonObject().entrySet().forEach(entry -> {
            jacksonComparator.compare(entry.getKey(), null, entry.getValue(), subEvent);
        });
        event.subEvents().add(event);

    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {

        return before == null && after != null && after.isJsonObject();
    }

}
