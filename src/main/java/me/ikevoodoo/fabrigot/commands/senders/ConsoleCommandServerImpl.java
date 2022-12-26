package me.ikevoodoo.fabrigot.commands.senders;

import net.minecraft.server.command.CommandOutput;
import net.minecraft.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public final class ConsoleCommandServerImpl implements ConsoleCommandSender {

    private final CommandOutput output;
    private final String name;

    public ConsoleCommandServerImpl(CommandOutput output, String name) {
        this.output = output;
        this.name = name;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.output.sendMessage(Text.of(message));
    }

    @Override
    public void sendMessage(@NotNull String... messages) {
        for (String msg : messages) {
            this.sendMessage(msg);
        }
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {
        // TODO Implement
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
        // TODO Implement
    }

    @Override
    public @NotNull Server getServer() {
        return Bukkit.getServer(); // TODO Implement
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull Spigot spigot() {
        return null; // TODO Implement
    }

    @Override
    public boolean isConversing() {
        return false; // TODO Implement
    }

    @Override
    public void acceptConversationInput(@NotNull String input) {
        // TODO Implement
    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return false; // TODO Implement
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {
        // TODO Implement
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details) {
        // TODO Implement
    }

    @Override
    public void sendRawMessage(@NotNull String message) {
        this.output.sendMessage(Text.literal(message));
    }

    @Override
    public void sendRawMessage(@Nullable UUID sender, @NotNull String message) {
        // TODO Implement
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return true;
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return true;
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return null; // Not needed: console has all perms.
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return null; // Not needed: console has all perms.
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return null; // Not needed: console has all perms.
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return null; // Not needed: console has all perms.
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
         // Not needed: console has all perms.
    }

    @Override
    public void recalculatePermissions() {
         // Not needed: console has all perms.
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return null; // TODO Implement
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {

    }
}
