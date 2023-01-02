package me.ikevoodoo.fabrigot.impl.org.bukkit.scheduler;

import me.ikevoodoo.fabrigot.Fabrigot;
import me.ikevoodoo.fabrigot.events.Events;
import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.Plugin;

public class SpigotRepeatingTask extends SpigotTask {

    private final long interval;
    private long start;
    private long executions;

    public SpigotRepeatingTask(Runnable runnable, int id, Plugin owner, boolean isSync, long interval) {
        super(runnable, id, owner, isSync);
        this.interval = interval;
    }

    public static SpigotRepeatingTask sync(Runnable runnable, int id, Plugin owner, long interval) {
        return new SpigotRepeatingTask(runnable, id, owner, true, interval);
    }

    public static SpigotRepeatingTask async(Runnable runnable, int id, Plugin owner, long interval) {
        return new SpigotRepeatingTask(runnable, id, owner, false, interval);
    }

    @Override
    public void runAfter(long delay) {
        this.start = Fabrigot.getMinecraftServer().getTicks() + delay;

        Events.START_SERVER_TICK_EVENT.addListener(this);
    }

    @Override
    public void accept(MinecraftServer server) {
        if (this.isCancelled()) return;

        if (server.getTicks() >= this.start + this.interval * this.executions) {
            this.run();
            this.executions++;
        }
    }
}
