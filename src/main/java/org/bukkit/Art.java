package org.bukkit;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public enum Art implements Keyed {

    KEBAB(1, 1),
    AZTEC(1, 1),
    ALBAN(1, 1),
    AZTEC2(1, 1),
    BOMB(1, 1),
    PLANT(1, 1),
    WASTELAND(1, 1),
    POOL(2, 1),
    COURBET(2, 1),
    SEA(2, 1),
    SUNSET(2, 1),
    CREEBET(2, 1),
    WANDERER(1, 2),
    GRAHAM(1, 2),
    MATCH(2, 2),
    BUST(2, 2),
    STAGE(2, 2),
    VOID(2, 2),
    SKULL_AND_ROSES(2, 2),
    WITHER(2, 2),
    FIGHTERS(4, 2),
    POINTER(4, 4),
    PIGSCENE(4, 4),
    BURNING_SKULL(4, 4),
    SKELETON(4, 3),
    DONKEY_KONG(4, 3);

    private final int id, width, height;
    private final NamespacedKey key;

    private static final HashMap<String, Art> BY_NAME = Maps.newHashMap();
    private static final HashMap<Integer, Art> BY_ID = Maps.newHashMap();

    private Art(int width, int height) {
        this.id = this.ordinal();
        this.width = width;
        this.height = height;
        this.key = NamespacedKey.minecraft(name().toLowerCase(java.util.Locale.ENGLISH));
    }


    /**
     * Gets the width of the painting, in blocks
     *
     * @return The width of the painting, in blocks
     */
    public int getBlockWidth() {
        return width;
    }

    /**
     * Gets the height of the painting, in blocks
     *
     * @return The height of the painting, in blocks
     */
    public int getBlockHeight() {
        return height;
    }

    /**
     * Get the ID of this painting.
     *
     * @return The ID of this painting
     * @deprecated Magic value
     */
    @Deprecated
    public int getId() {
        return id;
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Get a painting by its numeric ID
     *
     * @param id The ID
     * @return The painting
     * @deprecated Magic value
     */
    @Deprecated
    @Nullable
    public static Art getById(int id) {
        return BY_ID.get(id);
    }

    /**
     * Get a painting by its unique name
     * <p>
     * This ignores underscores and capitalization
     *
     * @param name The name
     * @return The painting
     */
    @Nullable
    public static Art getByName(@NotNull String name) {
        Validate.notNull(name, "Name cannot be null");

        return BY_NAME.get(name.toLowerCase(java.util.Locale.ENGLISH));
    }

    static {
        for (Art art : values()) {
            BY_ID.put(art.id, art);
            BY_NAME.put(art.toString().toLowerCase(java.util.Locale.ENGLISH), art);
        }
    }

}
