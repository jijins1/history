package ovh.ruokki.history.model.compare.jackson;


import ovh.ruokki.history.model.compare.Comparator;


import com.google.gson.JsonElement;

import ovh.ruokki.history.model.change.Event;

public interface ComparatorJacksonString implements Comparator<String>{
    
    Event compare(String before, String after);

}
