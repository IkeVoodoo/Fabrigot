package me.ikevoodoo.fabrigot.events;

import org.bukkit.util.Consumer;

public interface EventConsumer<T> extends Consumer<T> {

    default boolean isCancelled() {
        return false;
    }

}
