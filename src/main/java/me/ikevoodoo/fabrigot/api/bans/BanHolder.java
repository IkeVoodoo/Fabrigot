package me.ikevoodoo.fabrigot.api.bans;

import com.mojang.authlib.GameProfile;
import me.ikevoodoo.fabrigot.api.bans.lists.IpBanList;
import me.ikevoodoo.fabrigot.api.bans.lists.PlayerBanList;

public interface BanHolder {

    BannedPlayer getPlayerBanEntry(GameProfile profile);

    BannedIp getIpBanEntry(String ip);

    void addPlayerBanEntry(BannedPlayer entry);
    void addIpBanEntry(BannedIp entry);

    PlayerBanList getBannedPlayers();
    IpBanList getBannedIps();

    boolean isPlayerBanned(GameProfile profile);
    boolean isIpBanned(String ip);

    void pardonPlayer(GameProfile profile);
    void pardonIp(String ip);

}
