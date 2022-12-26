package me.ikevoodoo.fabrigot.mixins;

import me.ikevoodoo.fabrigot.FabrigotServer;
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
        FabrigotServer.postWorld();
    }

}
