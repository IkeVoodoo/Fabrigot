package me.ikevoodoo.fabrigot.impl.org.bukkit.scheduler;

import me.ikevoodoo.fabrigot.Fabrigot;
import me.ikevoodoo.fabrigot.events.Events;
import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.Plugin;

public class SpigotDelayedTask extends SpigotTask {

    private long start;

    public SpigotDelayedTask(Runnable runnable, int id, Plugin owner, boolean isSync) {
        super(runnable, id, owner, isSync);
    }

    public static SpigotDelayedTask sync(Runnable runnable, int id, Plugin owner) {
        return new SpigotDelayedTask(runnable, id, owner, true);
    }

    public static SpigotDelayedTask async(Runnable runnable, int id, Plugin owner) {
        return new SpigotDelayedTask(runnable, id, owner, false);
    }

    @Override
    public void runAfter(long delayTicks) {
        this.start = Fabrigot.getMinecraftServer().getTicks() + delayTicks;

        Events.START_SERVER_TICK_EVENT.addListener(this);
    }

    @Override
    public void accept(MinecraftServer server) {
        if (this.isCancelled()) return;

        if (server.getTicks() >= this.start) {
            this.run();
            this.cancel();
        }
    }
}
