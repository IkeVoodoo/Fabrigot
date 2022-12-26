package me.ikevoodoo.fabrigot.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.class)
public interface GameRulesAccessor {

    @Invoker
    <T extends GameRules.Rule<T>> void callSetValue(GameRules.Key<T> key, GameRules rules, @Nullable MinecraftServer server);

}
