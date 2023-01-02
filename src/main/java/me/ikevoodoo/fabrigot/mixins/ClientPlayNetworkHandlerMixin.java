package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Fabrigot;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.message.DecoratedContents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashSet;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "onChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/message/MessageHandler;onChatMessage(Lnet/minecraft/network/message/SignedMessage;Lnet/minecraft/network/message/MessageType$Parameters;)V"))
    private void redirectMessage(MessageHandler instance, SignedMessage message, MessageType.Parameters params) {
        var original = message.getContent().getString();

        var event = new AsyncPlayerChatEvent(
                true,
                Fabrigot.getFabrigotServer().convertPlayerEntity(this.client.player),
                original,
                new HashSet<>(Bukkit.getOnlinePlayers())
        );

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return; // Needed, doesn't affect game as if the event is cancelled a spam check shouldn't be performed.
        }

        SignedMessage sentMessage = !event.getMessage().equals(original)
                ? SignedMessage.ofUnsigned(new DecoratedContents(event.getMessage()))
                : message;

        instance.onChatMessage(sentMessage, params);
    }

}
