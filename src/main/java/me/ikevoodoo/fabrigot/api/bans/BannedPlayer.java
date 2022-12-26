package me.ikevoodoo.fabrigot.api.bans;

import com.mojang.authlib.GameProfile;
import me.ikevoodoo.fabrigot.mixins.ServerConfigEntryAccessor;
import net.minecraft.server.BannedPlayerEntry;
import org.bukkit.BanEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class BannedPlayer implements ServerBanEntry<GameProfile> {

    private final BannedPlayerEntry entry;

    public BannedPlayer(BannedPlayerEntry entry) {
        this.entry = entry;
    }

    public BannedPlayer(GameProfile profile, @Nullable Date created, @Nullable String source, @Nullable Date expiry, @Nullable String reason) {
        this(new BannedPlayerEntry(profile, created, source, expiry, reason));
    }

    @Override
    public GameProfile getKey() {
        return ((ServerConfigEntryAccessor<GameProfile>) this.entry).callGetKey();
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
    public BannedPlayer copy() {
        return new BannedPlayer(
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
            getKey().getId().toString(),
            getReason(),
            getSource(),
            getCreationDate(),
            getExpiryDate()
        );
    }

    @Override
    public BannedPlayerEntry toMinecraft() {
        return this.entry;
    }
}
