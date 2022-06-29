package ovh.ruokki.history.model.compare.jackson;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import ovh.ruokki.history.model.change.Event;

@Slf4j
public class JacksonObjectComparator implements JacksonComparator {

    private final JacksonElementComparator jacksonElementComparator;

    public JacksonObjectComparator(JacksonElementComparator jacksonElementComparator) {
        this.jacksonElementComparator = jacksonElementComparator;
    }

    @Override
    public void compare(String property, JsonElement before, JsonElement after, Event event) {
        log.debug("Compare object");
        Event subEvent = Event.empty();
        event.subEvents().add(subEvent);
        JsonObject beforeJsonObject = before.getAsJsonObject();
        JsonObject afterJsonObject = after.getAsJsonObject();
        Set<String> usedProperty = new HashSet<>();
        beforeJsonObject.entrySet().stream().forEach((entry) -> {
            String key = entry.getKey();
            log.debug("Compare object key : {}", key);
            JsonElement valueBefore = entry.getValue();
            usedProperty.add(key);
            JsonElement afterValue = afterJsonObject.get(key);
            if (valueBefore == null) {
                log.debug("Deletion detected : property : {}", key);
                jacksonElementComparator.compare(key, valueBefore, null, subEvent);
            } else {
                log.debug("Change detected : property : {}", key);

                jacksonElementComparator.compare(key, valueBefore, afterValue, subEvent);
            }

        });

        afterJsonObject.entrySet()
                .stream()
                .filter((entry) -> !usedProperty.contains(entry.getKey()))
                .forEach((entry) -> {

                    jacksonElementComparator.compare(entry.getKey(), null, entry.getValue(), subEvent);

                });

    }

    @Override
    public boolean canCompare(JsonElement before, JsonElement after) {
        return after != null && after.isJsonObject() && before != null && before.isJsonObject();
    }

}
