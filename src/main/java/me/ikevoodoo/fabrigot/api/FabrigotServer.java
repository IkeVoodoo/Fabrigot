package me.ikevoodoo.fabrigot.api;

import me.ikevoodoo.fabrigot.api.bans.BanHolder;
import me.ikevoodoo.fabrigot.commands.FabrigotCommandRegister;
import net.minecraft.server.network.ServerPlayerEntity;
import org.bukkit.entity.Player;

import java.util.List;

public interface FabrigotServer extends BanHolder {

//    CommandDispatcher<ServerCommandSource> getDispatcher();
//
//    CommandSender convertFromSource(ServerCommandSource source);
//
//    ServerCommandSource convertToSource(CommandSender sender);
//
//    Player getSpigotPlayer(UUID id);
//
//    void spigotToBrigadier(Command command);

    ServerPlayerEntity findPlayer(String target);

    Player getPlayer(ServerPlayerEntity entity);
    List<Player> getOnlinePlayers();

    String getVersion();

    FabrigotCommandRegister getCommandRegister();

}
