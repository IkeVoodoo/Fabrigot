package me.ikevoodoo.fabrigot.mixins;

import com.mojang.datafixers.DataFixer;
import me.ikevoodoo.fabrigot.Data;
import me.ikevoodoo.fabrigot.FabrigotServer;
import me.ikevoodoo.fabrigot.Utils;
import me.ikevoodoo.fabrigot.impl.VanillaFabrigotServer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.dedicated.ServerPropertiesLoader;
import net.minecraft.util.ApiServices;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(MinecraftDedicatedServer.class)
public class MinecraftDedicatedServerMixin {

    @Inject(method = "setupServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/MinecraftDedicatedServer;setPlayerManager(Lnet/minecraft/server/PlayerManager;)V", shift = At.Shift.AFTER))
    public void onInitServer(CallbackInfoReturnable<Boolean> cir) {
        Data.SERVER = (MinecraftDedicatedServer) ((Object) this);
        Data.SERVER_HOME = Data.SERVER.getRunDirectory();
        FabrigotServer.serverCreated(Data.SERVER, Data.SERVER_HOME);
    }

}
