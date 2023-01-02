package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.Fabrigot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "createWorlds", at = @At("TAIL"))
    protected void afterWorldCreation(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        Fabrigot.postWorld();
    }

    @Inject(method = "loadWorld", at = @At("HEAD"))
    protected void atServerStart(CallbackInfo ci) {
        Fabrigot.serverStarted((MinecraftServer) (Object) this);
    }
}
