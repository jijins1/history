package ovh.ruokki.history.model.compare.jackson;

import com.google.gson.JsonParser;

import ovh.ruokki.history.model.change.Event;
import ovh.ruokki.history.model.compare.Comparator;

public class ComparatorJacksonString implements Comparator<String> {

    private JacksonElementComparator jacksonElementComparator;

    public ComparatorJacksonString(JacksonElementComparator jacksonElementComparator) {
        this.jacksonElementComparator = jacksonElementComparator;
    }

    public Event compare(String before, String after) {
        return jacksonElementComparator.compare(JsonParser.parseString(before), JsonParser.parseString(after));
    }

}
