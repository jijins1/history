package ovh.ruokki.history.model.compare.jackson;

import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Deletion;
import ovh.ruokki.history.model.change.Event;

@Slf4j
public class JacksonComparatorDeletionPrimitive implements JacksonComparator {

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        log.debug("Deletion primitive property {}", property);
        event.deletion().add(new Deletion(property, before.getAsString()));
    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {

        return before != null && before.isJsonPrimitive() && after == null;
    }

}
