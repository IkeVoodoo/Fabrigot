package me.ikevoodoo.fabrigot.mixins;


import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {


    @Inject(method = "sendInternal", at = @At("HEAD"))
    private void handleOutgoing(Packet<?> packet, PacketCallbacks callbacks, NetworkState packetState, NetworkState currentState, CallbackInfo ci) {

    }

    @Inject(method = "handlePacket", at = @At("HEAD"))
    private static void handleReceiving(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket chatPacket) {

            var asyncMessageEvent = new AsyncPlayerChatEvent(
                    false,
                    null,
                    chatPacket.chatMessage(),
                    new HashSet<>(Bukkit.getOnlinePlayers())
            );
        }
    }

}
