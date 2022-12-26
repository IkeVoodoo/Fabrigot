package me.ikevoodoo.fabrigot;

import com.mojang.brigadier.CommandDispatcher;
import me.ikevoodoo.fabrigot.impl.VanillaFabrigotServer;
import me.ikevoodoo.fabrigot.impl.org.bukkit.SpigotServerImplementation;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

@Environment(EnvType.SERVER)
public class FabrigotServer implements DedicatedServerModInitializer {

    private static CommandDispatcher<ServerCommandSource> dispatcher;

    @Override
    public void onInitializeServer() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, registrationEnvironment) -> {
            FabrigotServer.dispatcher = dispatcher;
        });
    }

    public static void serverCreated(MinecraftDedicatedServer server, File serverHome) {
        Data.FABRIGOT_SERVER = new VanillaFabrigotServer(server, dispatcher);

        File directory = Utils.ensureDirectory(serverHome, "plugins");

        Bukkit.setServer(new SpigotServerImplementation(server.getThread()));

        Bukkit.getPluginManager().registerInterface(JavaPluginLoader.class);

        for (Plugin plugin : Bukkit.getPluginManager().loadPlugins(directory)) {
            plugin.onLoad();
        }

        enable(Bukkit.getPluginManager().getPlugins(), PluginLoadOrder.STARTUP);
    }

    public static void postWorld() {
        enable(Bukkit.getPluginManager().getPlugins(), PluginLoadOrder.POSTWORLD);
    }

    private static void enable(Plugin[] plugins, PluginLoadOrder order) {
        for (Plugin plugin : plugins) {
            if (!plugin.isEnabled() && plugin.getDescription().getLoad() == order) {
                Bukkit.getPluginManager().enablePlugin(plugin);
            }
        }
    }
}
