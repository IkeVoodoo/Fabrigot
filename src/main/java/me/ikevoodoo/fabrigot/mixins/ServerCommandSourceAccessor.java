package me.ikevoodoo.fabrigot.mixins;

import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerCommandSource.class)
public interface ServerCommandSourceAccessor {

    @Accessor
    CommandOutput getOutput();

}
