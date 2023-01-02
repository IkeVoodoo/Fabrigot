package org.bukkit.inventory;

import net.minecraft.util.Hand;

public enum EquipmentSlot {

    HAND,
    OFF_HAND,
    FEET,
    LEGS,
    CHEST,
    HEAD;

    public static EquipmentSlot fromHand(Hand hand) {
        return switch (hand) {
            case MAIN_HAND -> HAND;
            case OFF_HAND -> OFF_HAND;
        };
    }
}
