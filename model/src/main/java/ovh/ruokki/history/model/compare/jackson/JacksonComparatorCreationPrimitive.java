package ovh.ruokki.history.model.compare.jackson;

import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Creation;
import ovh.ruokki.history.model.change.Event;

@Slf4j
public class JacksonComparatorCreationPrimitive implements JacksonComparator {

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        log.debug("Creation primitive property {}", property);
        event.creations().add(new Creation(property, after.getAsString()));

    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {

        return before == null && after != null && after.isJsonPrimitive();
    }

}
