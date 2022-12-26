package me.ikevoodoo.fabrigot.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import me.ikevoodoo.fabrigot.api.FabrigotServer;
import me.ikevoodoo.fabrigot.api.bans.BannedIp;
import me.ikevoodoo.fabrigot.api.bans.BannedPlayer;
import me.ikevoodoo.fabrigot.api.bans.lists.IpBanList;
import me.ikevoodoo.fabrigot.api.bans.lists.PlayerBanList;
import me.ikevoodoo.fabrigot.commands.FabrigotCommandRegister;
import me.ikevoodoo.fabrigot.commands.FabrigotCommandTranslator;
import me.ikevoodoo.fabrigot.impl.org.bukkit.entity.SpigotPlayer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


public class VanillaFabrigotServer implements FabrigotServer {

    private final MinecraftDedicatedServer server;
    private final CommandDispatcher<ServerCommandSource> dispatcher;
    private final FabrigotCommandRegister fabrigotCommandRegister;

    public VanillaFabrigotServer(MinecraftDedicatedServer server, CommandDispatcher<ServerCommandSource> dispatcher) {
        this.server = server;
        this.dispatcher = dispatcher;
        this.fabrigotCommandRegister = new FabrigotCommandRegister(dispatcher, new FabrigotCommandTranslator(this));
    }

    @Override
    public BannedPlayer getPlayerBanEntry(GameProfile profile) {
        return new BannedPlayer(server.getPlayerManager().getUserBanList().get(profile));
    }

    @Override
    public BannedIp getIpBanEntry(String ip) {
        return new BannedIp(server.getPlayerManager().getIpBanList().get(ip));
    }

    @Override
    public void addPlayerBanEntry(BannedPlayer entry) {
        server.getPlayerManager().getUserBanList().add(entry.toMinecraft());
    }

    @Override
    public void addIpBanEntry(BannedIp entry) {
        server.getPlayerManager().getIpBanList().add(entry.toMinecraft());
    }

    @Override
    public PlayerBanList getBannedPlayers() {
        return new PlayerBanList(server.getPlayerManager().getUserBanList());
    }

    @Override
    public IpBanList getBannedIps() {
        return new IpBanList(server.getPlayerManager().getIpBanList());
    }

    @Override
    public boolean isPlayerBanned(GameProfile profile) {
        return getBannedPlayers().containsKey(profile);
    }

    @Override
    public boolean isIpBanned(String ip) {
        return getBannedIps().containsKey(ip);
    }

    @Override
    public void pardonPlayer(GameProfile profile) {
        this.getBannedPlayers().removeKey(profile);
    }

    @Override
    public void pardonIp(String ip) {
        this.getBannedIps().removeKey(ip);
    }

    @Override
    public ServerPlayerEntity findPlayer(String target) {
        try {
            return this.server.getPlayerManager().getPlayer(UUID.fromString(target));
        } catch (IllegalArgumentException e) {
            return this.server.getPlayerManager().getPlayer(target);
        }
    }

    @Override
    public Player getPlayer(ServerPlayerEntity entity) {
        return new SpigotPlayer(entity);
    }

    @Override
    public List<Player> getOnlinePlayers() {
        return this.server.getPlayerManager().getPlayerList().stream().map(this::getPlayer).toList();
    }

    @Override
    public String getVersion() {
        return this.server.getVersion();
    }

    @Override
    public FabrigotCommandRegister getCommandRegister() {
        return this.fabrigotCommandRegister;
    }

//    @Override
//    public CommandDispatcher<ServerCommandSource> getDispatcher() {
//        return this.dispatcher;
//    }
//
//    @Override
//    public CommandSender convertFromSource(ServerCommandSource source) {
//        return getSpigotPlayer(source.getPlayer().getUuid());
//    }
//
//    @Override
//    public ServerCommandSource convertToSource(CommandSender sender) {
//        return null;
//    }
//
//    @Override
//    public Player getSpigotPlayer(UUID id) {
//        ServerPlayerEntity entity = this.server.getPlayerManager().getPlayer(id);
//        return new SpigotPlayer(entity);
//    }
//
//    @Override
//    public void spigotToBrigadier(Command command) {
//        this.dispatcher.register(
//            literal(command.getName())
//                .then(
//                    argument("args", greedyString())
//                        .executes(context -> exec(command, context) ? 1 : 0)
//                )
//                .executes(context -> exec(command, context) ? 1 : 0)
//        );
//    }
//
//    private boolean exec(Command command, CommandContext<ServerCommandSource> context) {
//        String argString;
//        try {
//            argString = getString(context, "args");
//        } catch (IllegalArgumentException e) {
//            argString = "";
//        }
//        return command.execute(this.convertFromSource(context.getSource()), command.getLabel(), argString.split(" "));
//    }
}
