package me.ikevoodoo.fabrigot;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class Utils {

    private Utils() {

    }

    public static ServerPlayerEntity getPlayer(String name) {
        try {
            return Data.SERVER.getPlayerManager().getPlayer(UUID.fromString(name));
        } catch (Exception e) {
            return Data.SERVER.getPlayerManager().getPlayer(name);
        }
    }

}
