package me.ikevoodoo.fabrigot.api;

import me.ikevoodoo.fabrigot.api.bans.BanHolder;
import me.ikevoodoo.fabrigot.commands.FabrigotCommandRegister;
import net.minecraft.server.network.ServerPlayerEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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

    ServerPlayerEntity getPlayer(UUID id);
    ServerPlayerEntity findPlayer(String target);
    ServerPlayerEntity findPlayerExact(String target);

    Player convertPlayer(ServerPlayerEntity entity);
    List<Player> getOnlinePlayers();
    void playerDisconnected(ServerPlayerEntity entity);
    String getVersion();

    FabrigotCommandRegister getCommandRegister();

}
