package me.ikevoodoo.fabrigot.mixins;

import com.mojang.datafixers.util.Either;
import me.ikevoodoo.fabrigot.Data;
import me.ikevoodoo.fabrigot.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "trySleep", at = @At("RETURN"), cancellable = true)
    private void handleSleepEvent(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        var player = (ServerPlayerEntity) (Object) this;

        var result = Utils.fromSleepFailureReason(cir.getReturnValue().left().orElse(null));

        // TODO get block
        var event = new PlayerBedEnterEvent(Data.FABRIGOT_SERVER.convertPlayer(player), null, result);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled() || event.useBed() == Event.Result.DENY) {
            cir.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM));
        }

        if (event.useBed() == Event.Result.ALLOW) {
            cir.setReturnValue(Either.right(Unit.INSTANCE));
        }
    }

    @Inject(method = "wakeUp", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerChunkManager;sendToNearbyPlayers(Lnet/minecraft/entity/Entity;Lnet/minecraft/network/Packet;)V"), cancellable = true)
    private void handleWakeUpEvent(boolean skipSleepTimer, boolean updateSleepingPlayers, CallbackInfo ci) {
        var player = (ServerPlayerEntity) (Object) this;

        var event = new PlayerBedLeaveEvent(Data.FABRIGOT_SERVER.convertPlayer(player), null, true);
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            ci.cancel();
        }

        if (event.shouldSetSpawnLocation()) {
            // TODO
        }
    }



}
