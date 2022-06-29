package ovh.ruokki.history.model.compare.jackson;

import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Change;
import ovh.ruokki.history.model.change.Event;

@Slf4j
public class JacksonPrimitiveComparator implements JacksonComparator {

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        String beforeString = before.getAsString();
        String afterAsString = after.getAsString();
        if (beforeString.equals(afterAsString)) {
            log.debug("Not change detected for primitive property : {}", property);

        } else {
            log.debug("Change detected for primitive property : {}", property);
            event.changes().add(new Change(property, beforeString, afterAsString));
        }
        ;
    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {
        return after != null && after.isJsonPrimitive() && before != null && before.isJsonPrimitive();
    }

}
