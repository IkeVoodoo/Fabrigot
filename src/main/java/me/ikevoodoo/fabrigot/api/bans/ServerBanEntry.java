package me.ikevoodoo.fabrigot.api.bans;

import org.bukkit.BanEntry;

import java.util.Date;

public interface ServerBanEntry<T> {

    T getKey();

    String getReason();

    String getSource();

    Date getCreationDate();

    Date getExpiryDate();

    ServerBanEntry<T> copy();

    BanEntry toSpigot();

    net.minecraft.server.BanEntry<T> toMinecraft();
}
