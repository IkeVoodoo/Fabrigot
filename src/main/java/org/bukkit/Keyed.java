package org.bukkit;

import org.jetbrains.annotations.NotNull;

public interface Keyed {

    /**
     * Return the namespaced identifier for this object.
     *
     * @return this object's key
     */
    @NotNull
    NamespacedKey getKey();
}
