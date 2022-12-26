package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Data;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Unique
    private final ThreadLocal<ServerPlayerEntity> joiningPlayer = new ThreadLocal<>();

    @Inject(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    private void setJoiningPlayer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        this.joiningPlayer.set(player);
    }

    @ModifyArg(method = "onPlayerConnect", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V", ordinal = 0))
    private Text playerJoinEvent(Text message) {
        var event = new PlayerJoinEvent(Data.FABRIGOT_SERVER.getPlayer(this.joiningPlayer.get()), message.getString());
        this.joiningPlayer.remove();
        Bukkit.getPluginManager().callEvent(event);
        return Text.literal(event.getJoinMessage());
    }

}
