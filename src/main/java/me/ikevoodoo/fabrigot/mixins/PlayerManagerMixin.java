package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Fabrigot;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow public abstract void broadcast(Text message, boolean overlay);

    @Unique
    private final ThreadLocal<ServerPlayerEntity> joiningPlayer = new ThreadLocal<>();

    @Inject(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    private void setJoiningPlayer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        this.joiningPlayer.set(player);
    }

    @Redirect(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    private void playerJoinEvent(PlayerManager instance, Text message, boolean overlay) {
        var event = new PlayerJoinEvent(Fabrigot.getFabrigotServer().convertPlayerEntity(this.joiningPlayer.get()), message.getString());
        this.joiningPlayer.remove();
        Bukkit.getPluginManager().callEvent(event);

        if (event.getJoinMessage() != null && !event.getJoinMessage().isBlank()) {
            var newText = Text.literal(event.getJoinMessage());
            this.broadcast(newText, overlay);
        }
    }

}
