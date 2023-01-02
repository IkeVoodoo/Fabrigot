package me.ikevoodoo.fabrigot.impl.org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class SpigotScheduler implements BukkitScheduler {

    private final Map<Plugin, List<SpigotTask>> tasks = new ConcurrentHashMap<>();
    private final Executor schedulerThread;

    public SpigotScheduler() {
        this.schedulerThread = Executors.newCachedThreadPool();
    }

    @Override
    public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay) {
        var spigotTask = SpigotDelayedTask.sync(task, getNextId(plugin), plugin);
        this.setupTask(plugin, spigotTask);

        spigotTask.runAfter(delay);
        return spigotTask.getTaskId();
    }

    @Override
    public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay) {
        return this.scheduleSyncDelayedTask(plugin, (Runnable) task, delay);
    }

    @Override
    public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task) {
        return this.scheduleSyncDelayedTask(plugin, task, 0);
    }

    @Override
    public int scheduleSyncDelayedTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task) {
        return this.scheduleSyncDelayedTask(plugin, task, 0);
    }

    @Override
    public int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period) {
        var spigotTask = SpigotRepeatingTask.sync(task, getNextId(plugin), plugin, period);
        this.setupTask(plugin, spigotTask);

        spigotTask.runAfter(delay);
        return spigotTask.getTaskId();
    }

    @Override
    public int scheduleSyncRepeatingTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period) {
        return this.scheduleSyncRepeatingTask(plugin, (Runnable) task, delay, period);
    }

    @Override
    public int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay) {
        var spigotTask = SpigotDelayedTask.async(() -> this.schedulerThread.execute(task), getNextId(plugin), plugin);
        this.setupTask(plugin, spigotTask);

        spigotTask.runAfter(delay);
        return spigotTask.getTaskId();
    }

    @Override
    public int scheduleAsyncDelayedTask(@NotNull Plugin plugin, @NotNull Runnable task) {
        return this.scheduleAsyncDelayedTask(plugin, task, 0);
    }

    @Override
    public int scheduleAsyncRepeatingTask(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period) {
        var spigotTask = SpigotRepeatingTask.async(() -> this.schedulerThread.execute(task), getNextId(plugin), plugin, period);
        this.setupTask(plugin, spigotTask);

        spigotTask.runAfter(delay);
        return spigotTask.getTaskId();
    }

    @Override
    public @NotNull <T> Future<T> callSyncMethod(@NotNull Plugin plugin, @NotNull Callable<T> task) {
        return new CompletableFuture<>(); // TODO
    }


    @Override
    public void cancelTask(int taskId) {
        for (var list : this.tasks.values()) {
            for (Iterator<SpigotTask> iter = list.iterator(); iter.hasNext();) {
                var next = iter.next();
                if (next.getTaskId() == taskId) {
                    next.cancel();
                    iter.remove();
                    return;
                }
            }
        }
    }

    @Override
    public void cancelTasks(@NotNull Plugin plugin) {
        var list = this.tasks.remove(plugin);
        if (list == null) return;

        for (var task : list) {
            task.cancel();
        }

        list.clear();
    }

    @Override
    public boolean isCurrentlyRunning(int taskId) {
        return false; // TODO
    }

    @Override
    public boolean isQueued(int taskId) {
        return false; // TODO
    }

    @Override
    public @NotNull List<BukkitWorker> getActiveWorkers() {
        return new ArrayList<>(); // TODO
    }

    @Override
    public @NotNull List<BukkitTask> getPendingTasks() {
        return new ArrayList<>(); // TODO
    }

    @Override
    public @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull Runnable task) throws IllegalArgumentException {
        return this.runTaskLater(plugin, task, 0);
    }

    @Override
    public void runTask(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public @NotNull BukkitTask runTask(@NotNull Plugin plugin, @NotNull BukkitRunnable task) throws IllegalArgumentException {
        return this.runTask(plugin, (Runnable) task);
    }

    @Override
    public @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task) throws IllegalArgumentException {
       return this.runTaskLaterAsynchronously(plugin, task, 0);
    }

    @Override
    public void runTaskAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public @NotNull BukkitTask runTaskAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task) throws IllegalArgumentException {
        return this.runTaskAsynchronously(plugin, (Runnable) task);
    }

    @Override
    public @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull Runnable task, long delay) throws IllegalArgumentException {
        var spigotTask = SpigotDelayedTask.sync(task, getNextId(plugin), plugin);
        spigotTask.runAfter(delay);
        return spigotTask;
    }

    @Override
    public void runTaskLater(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public @NotNull BukkitTask runTaskLater(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay) throws IllegalArgumentException {
        return this.runTaskLater(plugin, (Runnable) task, delay);
    }

    @Override
    public @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task, long delay) throws IllegalArgumentException {
        var spigotTask = SpigotDelayedTask.async(() -> this.schedulerThread.execute(task), getNextId(plugin), plugin);
        spigotTask.runAfter(delay);
        return spigotTask;
    }

    @Override
    public void runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public @NotNull BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay) throws IllegalArgumentException {
        return this.runTaskLaterAsynchronously(plugin, (Runnable) task, 0);
    }

    @Override
    public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period) throws IllegalArgumentException {
        var spigotTask = SpigotRepeatingTask.sync(task, getNextId(plugin), plugin, period);
        spigotTask.runAfter(delay);
        return spigotTask;
    }

    @Override
    public void runTaskTimer(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public @NotNull BukkitTask runTaskTimer(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period) throws IllegalArgumentException {
        return this.runTaskTimer(plugin, (Runnable) task, delay, period);
    }

    @Override
    public @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Runnable task, long delay, long period) throws IllegalArgumentException {
        var spigotTask = SpigotRepeatingTask.async(() -> this.schedulerThread.execute(task), getNextId(plugin), plugin, period);
        spigotTask.runAfter(delay);
        return spigotTask;
    }

    @Override
    public void runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull Consumer<BukkitTask> task, long delay, long period) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public @NotNull BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, @NotNull BukkitRunnable task, long delay, long period) throws IllegalArgumentException {
        return this.runTaskTimerAsynchronously(plugin, (Runnable) task, delay, period);
    }

    private void setupTask(Plugin plugin, SpigotTask spigotTask) {
        spigotTask.getTaskCompleteEvent().addCallback(finishedTask -> {
            var list = this.tasks.get(plugin);
            if (list == null) return;

            list.remove(finishedTask);
        });

        spigotTask.getTaskCancelEvent().addCallback(cancelledTask -> {
            var list = this.tasks.get(plugin);
            if (list == null) return;

            list.remove(cancelledTask);
        });

        var list = this.tasks.getOrDefault(plugin, new CopyOnWriteArrayList<>());
        list.add(spigotTask);
        this.tasks.put(plugin, list);
    }

    private int getNextId(Plugin plugin) {
        var list = this.tasks.get(plugin);
        if (list == null) return -1;
        return list.size() + plugin.hashCode();
    }
}
