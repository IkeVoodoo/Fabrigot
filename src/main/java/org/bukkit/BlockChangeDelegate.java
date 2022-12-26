package org.bukkit;

import net.minecraft.util.math.BlockPos;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * A delegate for handling block changes. This serves as a direct interface
 * between generation algorithms in the server implementation and utilizing
 * code.
 */
public class BlockChangeDelegate {

    private final HashMap<Long, BlockData> datas = new HashMap<>();
    private final int height;

    public BlockChangeDelegate(int height) {
        this.height = height;
    }

    /**
     * Set a block data at the specified coordinates.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param blockData Block data
     * @return true if the block was set successfully
     */
    public boolean setBlockData(int x, int y, int z, @NotNull BlockData blockData) {
        Long value = BlockPos.asLong(x, y, z);
        this.datas.put(value, blockData);
        return true;
    }

    /**
     * Get the block data at the location.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return The block data
     */
    @NotNull
    public BlockData getBlockData(int x, int y, int z) {

        // TODO: make default value AIR or similar
        return this.datas.getOrDefault(BlockPos.asLong(x, y, z), null);
    }

    /**
     * Gets the height of the world.
     *
     * @return Height of the world
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Checks if the specified block is empty (air) or not.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return True if the block is considered empty.
     */
    public boolean isEmpty(int x, int y, int z) {

        // TODO: Check against AIR
        return this.getBlockData(x, y, z) ==  null;
    }
}
