package me.ikevoodoo.fabrigot.api.bans.lists;

import me.ikevoodoo.fabrigot.api.bans.BannedIp;
import net.minecraft.server.BannedIpList;
import org.bukkit.BanEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class IpBanList implements ServerBanList<BannedIp, String> {

    private final BannedIpList list;

    public IpBanList(BannedIpList list) {
        this.list = list;
    }

    @Override
    public Collection<BannedIp> values() {
        return list
            .values()
            .stream()
            .map(BannedIp::new)
            .collect(Collectors.toList());
    }

    @Override
    public boolean contains(BannedIp value) {
        return this.list.isBanned(value.getKey());
    }

    @Override
    public boolean containsKey(String key) {
        return this.list.isBanned(key);
    }

    @Override
    public void remove(BannedIp value) {
        this.removeKey(value.getKey());
    }

    @Override
    public void removeKey(String key) {
        this.list.remove(key);
    }

    @Override
    public @Nullable BanEntry getBanEntry(@NotNull String target) {
        return new BannedIp(this.list.get(target)).toSpigot();
    }

    @Override
    public @Nullable BanEntry addBan(@NotNull String target, @Nullable String reason, @Nullable Date expires, @Nullable String source) {
        var entry = new BannedIp(target, new Date(), source, expires, reason);
        this.list.add(entry.toMinecraft());
        return entry.toSpigot();
    }

    @Override
    public @NotNull Set<BanEntry> getBanEntries() {
        return this.list
                .values()
                .stream()
                .map(BannedIp::new)
                .map(BannedIp::toSpigot)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isBanned(@NotNull String target) {
        return this.containsKey(target);
    }

    @Override
    public void pardon(@NotNull String target) {
        this.removeKey(target);
    }
}
