package me.ikevoodoo.fabrigot.api.bans;

import me.ikevoodoo.fabrigot.mixins.ServerConfigEntryAccessor;
import net.minecraft.server.BannedIpEntry;
import org.bukkit.BanEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class BannedIp implements ServerBanEntry<String> {

    private final BannedIpEntry entry;

    public BannedIp(BannedIpEntry entry) {
        this.entry = entry;
    }

    public BannedIp(String ip, @Nullable Date created, @Nullable String source, @Nullable Date expiry, @Nullable String reason) {
        this(new BannedIpEntry(ip, created, source, expiry, reason));
    }

    @Override
    public String getKey() {
        return ((ServerConfigEntryAccessor<String>) entry).callGetKey();
    }

    @Override
    public String getReason() {
        return this.entry.getReason();
    }

    @Override
    public String getSource() {
        return this.entry.getSource();
    }

    @Override
    public Date getCreationDate() {
        return this.entry.getCreationDate();
    }

    @Override
    public Date getExpiryDate() {
        return this.entry.getExpiryDate();
    }

    @Override
    public BannedIp copy() {
        return new BannedIp(
            this.getKey(),
            this.getCreationDate(),
            this.getSource(),
            this.getExpiryDate(),
            this.getReason()
        );
    }

    @Override
    public BanEntry toSpigot() {
        return new BanEntry(
            getKey(),
            getReason(),
            getSource(),
            getCreationDate(),
            getExpiryDate()
        );
    }

    @Override
    public BannedIpEntry toMinecraft() {
        return this.entry;
    }
}
