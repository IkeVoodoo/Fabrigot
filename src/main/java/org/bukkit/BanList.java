package org.bukkit;

import com.mojang.authlib.GameProfile;
import me.ikevoodoo.fabrigot.Data;
import me.ikevoodoo.fabrigot.Utils;
import me.ikevoodoo.fabrigot.mixins.ServerConfigEntryAccessor;
import net.minecraft.server.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A ban list, containing bans of some {@link Type}.
 */
public class BanList {

    public BanList(Type banListType) {
        this.banListType = banListType;
    }

    /**
     * Represents a ban-type that a {@link BanList} may track.
     */
    public enum Type {
        /**
         * Banned player names
         */
        NAME,
        /**
         * Banned player IP addresses
         */
        IP,
        ;
    }

    private final Type banListType;


    /**
     * Gets a {@link BanEntry} by target.
     *
     * @param target entry parameter to search for
     * @return the corresponding entry, or null if none found
     */
    @Nullable
    public BanEntry getBanEntry(@NotNull String target) {
        PlayerManager manager = Data.SERVER.getPlayerManager();
        switch (this.banListType) {
            case NAME -> {
                ServerPlayerEntity entity = Utils.getPlayer(target);

                if (entity == null)
                    return null;

                BannedPlayerEntry entry = manager.getUserBanList().get(entity.getGameProfile());

                if (entry == null)
                    return null;

                return new BanEntry(
                        ((ServerConfigEntryAccessor<GameProfile>) entry).getKey().getId().toString(),
                        entry.getReason(),
                        entry.getSource(),
                        entry.getCreationDate(),
                        entry.getExpiryDate());
            }
            case IP -> {
                BannedIpEntry entry = manager.getIpBanList().get(target);

                if (entry == null)
                    return null;

                return new BanEntry(
                        ((ServerConfigEntryAccessor<String>) entry).getKey(),
                        entry.getReason(),
                        entry.getSource(),
                        entry.getCreationDate(),
                        entry.getExpiryDate());
            }
        }

        return null;
    }

    /**
     * Adds a ban to the this list. If a previous ban exists, this will
     * update the previous entry.
     *
     * @param target the target of the ban
     * @param reason reason for the ban, null indicates implementation default
     * @param expires date for the ban's expiration (unban), or null to imply
     *     forever
     * @param source source of the ban, null indicates implementation default
     * @return the entry for the newly created ban, or the entry for the
     *     (updated) previous ban
     */
    @Nullable
    public BanEntry addBan(@NotNull String target, @Nullable String reason, @Nullable Date expires, @Nullable String source) {
        BanEntry entry = new BanEntry(target, reason, source, new Date(), expires);
        PlayerManager manager = Data.SERVER.getPlayerManager();
        switch (this.banListType) {
            case NAME -> {
                ServerPlayerEntity entity = Utils.getPlayer(target);
                if (entity == null)
                    return null;

                BannedPlayerEntry playerEntry = new BannedPlayerEntry(
                        entity.getGameProfile(),
                        entry.getCreated(),
                        entry.getSource(),
                        entry.getExpiration(),
                        entry.getReason()
                );

                manager.getUserBanList().add(playerEntry);
            }
            case IP -> {
                BannedIpEntry ipEntry = new BannedIpEntry(
                        entry.getTarget(),
                        entry.getCreated(),
                        entry.getSource(),
                        entry.getExpiration(),
                        entry.getReason()
                );

                manager.getIpBanList().add(ipEntry);
            }
        }

        return entry;
    }

    /**
     * Gets a set containing every {@link BanEntry} in this list.
     *
     * @return an immutable set containing every entry tracked by this list
     */
    @NotNull
    public Set<BanEntry> getBanEntries() {
        PlayerManager manager = Data.SERVER.getPlayerManager();
        switch (this.banListType) {
            case NAME -> {
                return manager.getUserBanList()
                        .values()
                        .stream()
                        .map(banEntry -> new BanEntry(
                                ((ServerConfigEntryAccessor<GameProfile>) banEntry).getKey().getId().toString(),
                                banEntry.getReason(),
                                banEntry.getSource(),
                                banEntry.getCreationDate(),
                                banEntry.getExpiryDate()))
                        .collect(Collectors.toSet());
            }
            case IP -> {
                return manager.getIpBanList()
                        .values()
                        .stream()
                        .map(banEntry -> new BanEntry(
                                ((ServerConfigEntryAccessor<String>) banEntry).getKey(),
                                banEntry.getReason(),
                                banEntry.getSource(),
                                banEntry.getCreationDate(),
                                banEntry.getExpiryDate()))
                        .collect(Collectors.toSet());
            }
        }

        return Set.of();
    }

    /**
     * Gets if a {@link BanEntry} exists for the target, indicating an active
     * ban status.
     *
     * @param target the target to find
     * @return true if a {@link BanEntry} exists for the name, indicating an
     *     active ban status, false otherwise
     */
    public boolean isBanned(@NotNull String target) {
        PlayerManager manager = Data.SERVER.getPlayerManager();
        switch (this.banListType) {
            case NAME -> {
                ServerPlayerEntity entity = Utils.getPlayer(target);

                if (entity == null)
                    return false;

                return manager.getUserBanList().contains(entity.getGameProfile());
            }
            case IP -> {
                return manager.getIpBanList().isBanned(target);
            }
        }

        return false;
    }

    /**
     * Removes the specified target from this list, therefore indicating a
     * "not banned" status.
     *
     * @param target the target to remove from this list
     */
    public void pardon(@NotNull String target) {
        PlayerManager manager = Data.SERVER.getPlayerManager();
        switch (this.banListType) {
            case NAME -> {
                ServerPlayerEntity entity = Utils.getPlayer(target);

                if (entity == null)
                    return;

                manager.getUserBanList().remove(entity.getGameProfile());
            }
            case IP -> manager.getIpBanList().remove(target);
        }
    }
}
