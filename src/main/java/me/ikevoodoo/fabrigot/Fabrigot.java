package me.ikevoodoo.fabrigot;

import com.mojang.brigadier.CommandDispatcher;
import me.ikevoodoo.fabrigot.api.FabrigotServer;
import me.ikevoodoo.fabrigot.impl.VanillaFabrigotServer;
import me.ikevoodoo.fabrigot.impl.org.bukkit.SpigotServerImplementation;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class Fabrigot implements ModInitializer {
    private static FabrigotServer FABRIGOT_SERVER;
    private static File SERVER_HOME;
    private static MinecraftServer MINECRAFT_SERVER;

    private static CommandDispatcher<ServerCommandSource> dispatcher;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, registrationEnvironment) -> {
            Fabrigot.dispatcher = dispatcher;
        });
    }

    public static FabrigotServer getFabrigotServer() {
        return FABRIGOT_SERVER;
    }

    public static File getServerHome() {
        return SERVER_HOME;
    }

    public static MinecraftServer getMinecraftServer() {
        return MINECRAFT_SERVER;
    }

    public static void postWorld() {
        enable(Bukkit.getPluginManager().getPlugins(), PluginLoadOrder.POSTWORLD);
    }

    public static void serverStarted(MinecraftServer server) {
        FABRIGOT_SERVER = new VanillaFabrigotServer(server, dispatcher);
        SERVER_HOME = server.getRunDirectory();
        MINECRAFT_SERVER = server;

        File directory = Utils.ensureDirectory(server.getRunDirectory(), "plugins");

        Bukkit.setServer(new SpigotServerImplementation(server));

        Bukkit.getPluginManager().registerInterface(JavaPluginLoader.class);

        for (Plugin plugin : Bukkit.getPluginManager().loadPlugins(directory)) {
            plugin.onLoad();
        }

        enable(Bukkit.getPluginManager().getPlugins(), PluginLoadOrder.STARTUP);
    }

    private static void enable(Plugin[] plugins, PluginLoadOrder order) {
        for (Plugin plugin : plugins) {
            if (!plugin.isEnabled() && plugin.getDescription().getLoad() == order) {
                Bukkit.getPluginManager().enablePlugin(plugin);
            }
        }
    }
}
