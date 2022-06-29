package ovh.ruokki.history.model.compare.jackson;

import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Event;

@Slf4j
public class JacksonComparatorDeletionObject implements JacksonComparator {

    private JacksonElementComparator jacksonComparator;

    public JacksonComparatorDeletionObject(JacksonElementComparator jacksonComparator) {
        this.jacksonComparator = jacksonComparator;
    }

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        log.debug("Deletion object property {}", property);
        Event subEvent = Event.empty();
        before.getAsJsonObject().entrySet().forEach(entry -> {
            jacksonComparator.compare(entry.getKey(), entry.getValue(), null, subEvent);
        });
        event.subEvents().add(event);
    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {

        return before != null && before.isJsonObject() && after == null;
    }

}
