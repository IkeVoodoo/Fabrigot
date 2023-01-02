package me.ikevoodoo.fabrigot.commands;

import me.ikevoodoo.fabrigot.api.FabrigotServer;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import org.bukkit.command.CommandSender;

public final class FabrigotCommandTranslator {

    private final FabrigotServer fabrigotServer;

    public FabrigotCommandTranslator(FabrigotServer fabrigotServer) {
        this.fabrigotServer = fabrigotServer;
    }

    public CommandSender toSpigot(CommandSource source) {
        if (source instanceof ServerCommandSource serverCommandSource) {
            if (serverCommandSource.isExecutedByPlayer()) {
                return this.fabrigotServer.convertPlayer(serverCommandSource.getPlayer());
            }

//            Entity entity = serverCommandSource.getEntity();
//            if (entity != null) {
//                return this.entitySender(entity);
//            }
//
//            CommandOutput output = ((ServerCommandSourceAccessor) serverCommandSource).getOutput();
//            if (output instanceof CommandBlockExecutor) {
//                return this.commandBlock(serverCommandSource.getWorld(), serverCommandSource.getPosition());
//            }
//
//            if (output instanceof RconCommandOutput rcon) {
//                return this.rcon(rcon);
//            }
//
//            if (output instanceof MinecraftServer server) {
//                return this.console(server);
//            }
        }

        return null;
    }

}
