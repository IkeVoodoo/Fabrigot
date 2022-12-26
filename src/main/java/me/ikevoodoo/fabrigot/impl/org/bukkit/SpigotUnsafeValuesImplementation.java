package me.ikevoodoo.fabrigot.impl.org.bukkit;

import net.minecraft.advancement.Advancement;
import net.minecraft.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.UnsafeValues;
import org.bukkit.block.data.BlockData;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginDescriptionFile;

public class SpigotUnsafeValuesImplementation implements UnsafeValues {
    @Override
    public Material toLegacy(Material material) {
        return null;
    }

    @Override
    public Material fromLegacy(Material material) {
        return null;
    }

    @Override
    public Material fromLegacy(MaterialData material) {
        return null;
    }

    @Override
    public Material fromLegacy(MaterialData material, boolean itemPriority) {
        return null;
    }

    @Override
    public BlockData fromLegacy(Material material, byte data) {
        return null;
    }

    @Override
    public Material getMaterial(String material, int version) {
        return null;
    }

    @Override
    public int getDataVersion() {
        return 0;
    }

    @Override
    public ItemStack modifyItemStack(ItemStack stack, String arguments) {
        return null;
    }

    @Override
    public void checkSupported(PluginDescriptionFile pdf) throws InvalidPluginException {

    }

    @Override
    public byte[] processClass(PluginDescriptionFile pdf, String path, byte[] clazz) {
        return clazz;
    }

    @Override
    public Advancement loadAdvancement(NamespacedKey key, String advancement) {
        return null;
    }

    @Override
    public boolean removeAdvancement(NamespacedKey key) {
        return false;
    }
}
