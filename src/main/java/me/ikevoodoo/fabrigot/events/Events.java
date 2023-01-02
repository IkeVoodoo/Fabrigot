package me.ikevoodoo.fabrigot.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

public final class Events {

    public static final Event<MinecraftServer> START_SERVER_TICK_EVENT = new Event<>();

    static {
        ServerTickEvents.START_SERVER_TICK.register(START_SERVER_TICK_EVENT::fire);
    }

    private Events() {

    }

}
