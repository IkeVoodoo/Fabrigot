package me.ikevoodoo.fabrigot.events;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Event<T> {

    private final List<EventConsumer<T>> listeners = new CopyOnWriteArrayList<>();
    private final List<EventConsumer<T>> callbacks = new CopyOnWriteArrayList<>();

    public void addListener(final EventConsumer<T> consumer) {
        this.listeners.add(consumer);
    }

    public void removeListener(final EventConsumer<T> consumer) {
        this.listeners.remove(consumer);
    }

    public void addCallback(EventConsumer<T> consumer) {
        this.callbacks.add(consumer);
    }

    public void fire(final T value) {
        var set = new HashSet<EventConsumer<T>>();
        this.listeners.forEach(listener -> {
            listener.accept(value);
            if (listener.isCancelled()) set.add(listener);
        });

        this.listeners.removeAll(set);
        set.clear();

        this.callbacks.forEach(listener -> listener.accept(value));
        this.callbacks.clear();
    }

}
