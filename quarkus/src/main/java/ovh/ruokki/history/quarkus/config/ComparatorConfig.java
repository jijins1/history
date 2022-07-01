package ovh.ruokki.history.quarkus.config;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.google.gson.JsonElement;

import ovh.ruokki.history.model.compare.Comparator;
import ovh.ruokki.history.model.compare.jackson.JacksonComparator;
import ovh.ruokki.history.model.compare.jackson.JacksonComparatorCreationObject;
import ovh.ruokki.history.model.compare.jackson.JacksonComparatorCreationPrimitive;
import ovh.ruokki.history.model.compare.jackson.JacksonComparatorDeletionObject;
import ovh.ruokki.history.model.compare.jackson.JacksonComparatorDeletionPrimitive;
import ovh.ruokki.history.model.compare.jackson.JacksonElementComparator;
import ovh.ruokki.history.model.compare.jackson.JacksonObjectComparator;
import ovh.ruokki.history.model.compare.jackson.JacksonPrimitiveComparator;

@ApplicationScoped
public class ComparatorConfig {
    @Produces
    Comparator<JsonElement> comparator(){
        ArrayList<JacksonComparator> jacksonComparators = new ArrayList<>();
        var jacksonComparator = new JacksonElementComparator(jacksonComparators);

        jacksonComparators.add(new JacksonPrimitiveComparator());
        jacksonComparators.add(new JacksonObjectComparator(jacksonComparator));
        jacksonComparators.add(new JacksonComparatorCreationPrimitive());
        jacksonComparators.add(new JacksonComparatorCreationObject(jacksonComparator));
        jacksonComparators.add(new JacksonComparatorDeletionObject(jacksonComparator));
        jacksonComparators.add(new JacksonComparatorDeletionPrimitive());

        return jacksonComparator;
    }
}
