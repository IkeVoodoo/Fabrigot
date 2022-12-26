package me.ikevoodoo.fabrigot.impl.org.bukkit;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public class SpigotImplementation extends Server.Spigot {

    private final Server server;

    public SpigotImplementation(Server server) {
        this.server = server;
    }

    @Override
    public @NotNull YamlConfiguration getConfig() {
        return super.getConfig();
    }

    @Override
    public void broadcast(@NotNull BaseComponent component) {
        this.server.broadcastMessage(TextComponent.toLegacyText(component));
    }

    @Override
    public void broadcast(@NotNull BaseComponent... components) {
        this.server.broadcastMessage(TextComponent.toLegacyText(components));
    }

    @Override
    public void restart() {
        super.restart();
    }
}
