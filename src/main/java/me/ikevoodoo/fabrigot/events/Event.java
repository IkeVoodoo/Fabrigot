package me.ikevoodoo.fabrigot.events;

import me.ikevoodoo.fabrigot.events.EventConsumer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.HashSet;
import java.util.Set;

public class Event<T> {

    private final Set<EventConsumer<T>> listeners = new HashSet<>();
    private final Set<EventConsumer<T>> callbacks = new HashSet<>();

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
