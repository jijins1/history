package ovh.ruokki.history.model.change;

import java.util.List;

public record Event(List<Change> changes, List<Creation> creations, List<Deletion> Deletion) {
    
}
