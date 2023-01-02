package me.ikevoodoo.fabrigot.api.bans.lists;

import com.mojang.authlib.GameProfile;
import me.ikevoodoo.fabrigot.Fabrigot;
import me.ikevoodoo.fabrigot.api.bans.BannedPlayer;
import net.minecraft.server.BannedPlayerList;
import org.bukkit.BanEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerBanList implements ServerBanList<BannedPlayer, GameProfile> {

    private final BannedPlayerList list;

    public PlayerBanList(BannedPlayerList list) {
        this.list = list;
    }

    @Override
    public Collection<BannedPlayer> values() {
        return list
            .values()
            .stream()
            .map(BannedPlayer::new)
            .collect(Collectors.toList());
    }


    @Override
    public boolean contains(BannedPlayer value) {
        return this.list.contains(value.getKey());
    }

    @Override
    public boolean containsKey(GameProfile key) {
        return this.list.contains(key);
    }

    @Override
    public void remove(BannedPlayer value) {
        this.removeKey(value.getKey());
    }

    @Override
    public void removeKey(GameProfile key) {
        this.list.remove(key);
    }

    @Override
    public @Nullable BanEntry getBanEntry(@NotNull String target) {
        return new BannedPlayer(this.list.get(Fabrigot.getFabrigotServer().findPlayerEntity(target).getGameProfile())).toSpigot();
    }

    @Override
    public @Nullable BanEntry addBan(@NotNull String target, @Nullable String reason, @Nullable Date expires, @Nullable String source) {
        var entry = new BannedPlayer(Fabrigot.getFabrigotServer().findPlayerEntity(target).getGameProfile(), new Date(), source, expires, reason);
        this.list.add(entry.toMinecraft());
        return entry.toSpigot();
    }

    @Override
    public @NotNull Set<BanEntry> getBanEntries() {
        return this.list
                .values()
                .stream()
                .map(BannedPlayer::new)
                .map(BannedPlayer::toSpigot)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isBanned(@NotNull String target) {
        return this.containsKey(Fabrigot.getFabrigotServer().findPlayerEntity(target).getGameProfile());
    }

    @Override
    public void pardon(@NotNull String target) {
        this.removeKey(Fabrigot.getFabrigotServer().findPlayerEntity(target).getGameProfile());
    }
}
