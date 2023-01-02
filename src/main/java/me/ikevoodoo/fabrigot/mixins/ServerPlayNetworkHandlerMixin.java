package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Data;
import net.minecraft.network.message.DecoratedContents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
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
import java.util.concurrent.CompletableFuture;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Shadow @Final private MinecraftServer server;

    @Shadow protected abstract void checkForSpam();

    @Shadow public abstract ServerPlayerEntity getPlayer();

    @Redirect(method = "onDisconnected", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Z)V"))
    private void playerDisconnected(PlayerManager instance, Text message, boolean overlay) {
        var event = new PlayerQuitEvent(Data.FABRIGOT_SERVER.convertPlayer(this.player), message.getString());
        Bukkit.getPluginManager().callEvent(event);
        if (event.getQuitMessage() != null && !event.getQuitMessage().isBlank()) {
            var newText = Text.literal(event.getQuitMessage());
            this.server.getPlayerManager().broadcast(newText, overlay);
        }
    }

    @Inject(method = "handleDecoratedMessage", at = @At("HEAD"), cancellable = true)
    private void handleMessage(SignedMessage message, CallbackInfo ci) {
        CompletableFuture.runAsync(() -> {
            var original = message.getContent().getString();

            var event = new AsyncPlayerChatEvent(
                    true,
                    Data.FABRIGOT_SERVER.convertPlayer(this.player),
                    original,
                    new HashSet<>(Bukkit.getOnlinePlayers())
            );

            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return; // Needed, doesn't affect game as if the event is cancelled a spam check shouldn't be performed.
            }

            var params = MessageType.params(MessageType.CHAT, this.player);
            SignedMessage sentMessage = !event.getMessage().equals(original)
                    ? SignedMessage.ofUnsigned(new DecoratedContents(event.getMessage()))
                    : message;

            this.server.getPlayerManager().broadcast(sentMessage, this.player, params);
            this.checkForSpam();
        });
        ci.cancel();
    }

}
