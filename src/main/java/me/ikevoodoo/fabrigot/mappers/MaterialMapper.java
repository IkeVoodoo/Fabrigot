package me.ikevoodoo.fabrigot.mappers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.bukkit.Material;

import java.util.HashMap;

public class MaterialMapper {

    private MaterialMapper() {

    }

    private static final HashMap<Material, Item> MATERIAL_TO_ITEM = new HashMap<>();
    private static final HashMap<Material, Block> MATERIAL_TO_BLOCK = new HashMap<>();

    static {
        for (Material material : Material.values()) {
            if (material.isItem())
                MATERIAL_TO_ITEM.put(material, NameMapper.byName(Items.class, material.name()));

            if (material.isBlock())
                MATERIAL_TO_BLOCK.put(material, NameMapper.byName(Blocks.class, material.name()));
        }
    }
}
