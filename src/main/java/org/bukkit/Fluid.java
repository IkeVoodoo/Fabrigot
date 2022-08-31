package org.bukkit;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum Fluid implements Keyed {

    WATER,
    FLOWING_WATER,
    LAVA,
    FLOWING_LAVA;

    private final NamespacedKey key;

    private Fluid() {
        this.key = NamespacedKey.minecraft(this.name().toLowerCase(Locale.ROOT));
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return key;
    }
}
