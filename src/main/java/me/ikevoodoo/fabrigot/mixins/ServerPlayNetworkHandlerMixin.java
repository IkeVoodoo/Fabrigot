package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Data;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Shadow protected abstract SignedMessage getSignedMessage(ChatMessageC2SPacket packet);

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "onChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;canAcceptMessage(Ljava/lang/String;Ljava/time/Instant;Lnet/minecraft/network/message/LastSeenMessageList$Acknowledgment;)Z"), cancellable = true)
    private void handleMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        var event = new AsyncPlayerChatEvent(
                true,
                Data.FABRIGOT_SERVER.getPlayer(this.player),
                this.getSignedMessage(packet).getSignedContent().plain(),
                new HashSet<>(Bukkit.getOnlinePlayers())
        );

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            ci.cancel();
        }

        // TODO allow editing
    }

    @Redirect(method = "onDisconnected", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    private void playerDisconnected(PlayerManager instance, Text message, boolean overlay) {
        var event = new PlayerQuitEvent(Data.FABRIGOT_SERVER.getPlayer(this.player), message.getString());
        Bukkit.getPluginManager().callEvent(event);
        if (event.getQuitMessage() != null && !event.getQuitMessage().isBlank()) {
            var newText = Text.literal(event.getQuitMessage());
            this.server.getPlayerManager().broadcast(newText, overlay);
        }
    }

}
