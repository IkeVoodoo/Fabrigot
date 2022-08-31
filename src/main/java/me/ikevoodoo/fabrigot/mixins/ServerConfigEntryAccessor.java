package me.ikevoodoo.fabrigot.mixins;

import net.minecraft.server.ServerConfigEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerConfigEntry.class)
public interface ServerConfigEntryAccessor<T> {

    @Invoker("getKey")
    T getKey();

}
