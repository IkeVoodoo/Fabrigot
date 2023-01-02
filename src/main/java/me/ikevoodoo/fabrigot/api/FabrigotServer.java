package me.ikevoodoo.fabrigot.api;

import me.ikevoodoo.fabrigot.api.bans.BanHolder;
import me.ikevoodoo.fabrigot.commands.FabrigotCommandRegister;
import net.minecraft.entity.player.PlayerEntity;
import org.bukkit.GameMode;
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

    PlayerEntity getPlayerEntity(UUID id);
    PlayerEntity findPlayerEntityExact(String target);
    PlayerEntity findPlayerEntity(String target);

    Player convertPlayerEntity(PlayerEntity entity);
    List<Player> getOnlinePlayers();
    void playerDisconnected(PlayerEntity entity);
    String getVersion();

    FabrigotCommandRegister getCommandRegister();

}
