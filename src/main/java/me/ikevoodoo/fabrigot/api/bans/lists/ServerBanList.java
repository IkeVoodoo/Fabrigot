package me.ikevoodoo.fabrigot.api.bans.lists;

import org.bukkit.BanEntry;
import org.bukkit.BanList;

import java.util.Collection;
import java.util.Set;

public interface ServerBanList<T, K> extends BanList {

    Collection<T> values();

    boolean contains(T value);

    boolean containsKey(K key);

    void remove(T value);

    void removeKey(K key);

}
