package me.ikevoodoo.fabrigot.impl.org.bukkit.scheduler;

import me.ikevoodoo.fabrigot.events.Event;
import me.ikevoodoo.fabrigot.events.EventConsumer;
import net.minecraft.server.MinecraftServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public abstract class SpigotTask extends BukkitRunnable implements BukkitTask, EventConsumer<MinecraftServer> {

    private final Runnable runnable;
    private final int id;
    private final Plugin owner;
    private final boolean isSync;
    private final Event<SpigotTask> taskCompleteEvent;
    private final Event<SpigotTask> taskCancelEvent;
    private boolean isCancelled;

    public SpigotTask(Runnable runnable, int id, Plugin owner, boolean isSync) {
        this.runnable = runnable;
        this.id = id;
        this.owner = owner;
        this.isSync = isSync;

        this.taskCompleteEvent = new Event<>();
        this.taskCancelEvent = new Event<>();
    }

    @Override
    public int getTaskId() {
        return this.id;
    }

    @Override
    public @NotNull Plugin getOwner() {
        return this.owner;
    }

    @Override
    public boolean isSync() {
        return this.isSync;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void cancel() {
        this.isCancelled = true;
        this.getTaskCancelEvent().fire(this);
    }

    public abstract void runAfter(long delayTicks);

    @Override
    public void run() {
        this.getRunnable().run();
        this.getTaskCancelEvent().fire(this);
    }

    public final Event<SpigotTask> getTaskCompleteEvent() {
        return this.taskCompleteEvent;
    }

    public final Event<SpigotTask> getTaskCancelEvent() {
        return this.taskCancelEvent;
    }

    protected final Runnable getRunnable() {
        return this.runnable;
    }
}
