package ovh.ruokki.history.model.compare.jackson;


import com.google.gson.JsonElement;

import ovh.ruokki.history.model.change.Event;

public interface JacksonComparator{
    
    void compare(String property, JsonElement before, JsonElement after, Event event);
    boolean canCompare(JsonElement before, JsonElement after);

}
