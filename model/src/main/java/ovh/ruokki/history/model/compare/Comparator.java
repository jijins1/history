package ovh.ruokki.history.model.compare;

import ovh.ruokki.history.model.change.Event;

public interface Comparator<T> {
    Event compare(T before, T after);
}