package ovh.ruokki.history.model.compare.jackson;

import java.io.IOException;
import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ovh.ruokki.history.model.change.Change;
import ovh.ruokki.history.model.change.Creation;
import ovh.ruokki.history.model.change.Deletion;
import ovh.ruokki.history.model.change.Event;

class JComparatorTest {

    private JacksonElementComparator jacksonComparator;

    @BeforeEach
    public void init() {

        ArrayList<JacksonComparator> jacksonComparators = new ArrayList<>();
        jacksonComparator = new JacksonElementComparator(jacksonComparators);

        jacksonComparators.add(new JacksonPrimitiveComparator());
        jacksonComparators.add(new JacksonObjectComparator(jacksonComparator));
        jacksonComparators.add(new JacksonComparatorCreationPrimitive());
        jacksonComparators.add(new JacksonComparatorCreationObject(jacksonComparator));
        jacksonComparators.add(new JacksonComparatorDeletionObject(jacksonComparator));
        jacksonComparators.add(new JacksonComparatorDeletionPrimitive());
    }

    @Test
    public void itShouldCompareJsonChange() throws IOException {
        var json1 = "{\"a\":1}";
        var json2 = "{\"a\":2}";
        JsonElement jsonElement = JsonParser.parseString(json1);
        JsonElement jsonElement2 = JsonParser.parseString(json2);
        Event event = jacksonComparator.compare(jsonElement, jsonElement2);
        Event expectedEvent = Event.empty();
        expectedEvent.changes().add(new Change("a", "1", "2"));

        Assertions.assertThat(event.subEvents().get(0)).isEqualTo(expectedEvent);
    }

    @Test
    public void itShouldCompareJsonAdd() throws IOException {
        var json1 = "{\"a\":1}";
        var json2 = "{\"a\":1, \"b\":87}";
        JsonElement jsonElement = JsonParser.parseString(json1);
        JsonElement jsonElement2 = JsonParser.parseString(json2);
        Event event = jacksonComparator.compare(jsonElement, jsonElement2);
        Event expectedEvent = Event.empty();
        expectedEvent.creations().add(new Creation("b", "87"));
        Assertions.assertThat(event.subEvents().get(0)).isEqualTo(expectedEvent);

    }

    @Test
    public void itShouldCompareJsonObjectAdd() throws IOException {
        var json1 = "{\"a\":1}";
        var json2 = """
                {
                    "a": 1,
                    "b":{
                        "c": 78
                    }
                }
                """;
        JsonElement jsonElement = JsonParser.parseString(json1);
        JsonElement jsonElement2 = JsonParser.parseString(json2);
        Event event = jacksonComparator.compare(jsonElement, jsonElement2);
        Event expectedEvent = Event.empty();
        Event subEventExpected = Event.empty();
        subEventExpected.creations().add(new Creation("c", "78"));
        expectedEvent.subEvents().add(subEventExpected);
        Assertions.assertThat(event.subEvents().get(0)).isEqualTo(expectedEvent);
    }
    
    @Test
    public void itShouldCompareJsonObjectDeletion() throws IOException {
        var json1 = "{\"a\":1}";
        var json2 = """
                {
                    "a": 1,
                    "b":{
                        "c": 78
                    }
                }
                """;
        JsonElement jsonElement = JsonParser.parseString(json1);
        JsonElement jsonElement2 = JsonParser.parseString(json2);
        Event event = jacksonComparator.compare(jsonElement2, jsonElement);
        Event expectedEvent = Event.empty();
        Event subEventExpected = Event.empty();
        subEventExpected.deletion().add(new Deletion("c", "78"));
        expectedEvent.subEvents().add(subEventExpected);
        Assertions.assertThat(event.subEvents().get(0)).isEqualTo(expectedEvent);
    }
}
