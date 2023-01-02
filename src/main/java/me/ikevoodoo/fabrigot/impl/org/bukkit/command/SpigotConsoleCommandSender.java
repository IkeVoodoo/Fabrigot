package me.ikevoodoo.fabrigot.impl.org.bukkit.command;

import me.ikevoodoo.fabrigot.Data;
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

public class SpigotConsoleCommandSender implements ConsoleCommandSender {
    @Override
    public void sendMessage(@NotNull String message) {

    }

    @Override
    public void sendMessage(@NotNull String... messages) {

    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {

    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {

    }

    @Override
    public @NotNull Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public @NotNull String getName() {
        return "Console";
    }

    @Override
    public @NotNull Spigot spigot() {
        return null; // TODO implement
    }

    // region ConversationAPI
    @Override
    public boolean isConversing() {
        return false;
    }

    @Override
    public void acceptConversationInput(@NotNull String input) {

    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return false;
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {

    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details) {

    }
    // endregion

    @Override
    public void sendRawMessage(@NotNull String message) {

    }

    @Override
    public void sendRawMessage(@Nullable UUID sender, @NotNull String message) {

    }

    // region Permissions
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
        return new PermissionAttachment(plugin, this);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return new PermissionAttachment(plugin, this);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return null;
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return null;
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return Set.of();
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {

    }
    // endregion
}
