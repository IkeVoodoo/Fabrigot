package org.bukkit;

import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Represents a chunk of blocks
 */
public class Chunk extends PersistentDataHolder {

    private final int x, y;
    private final World world;

    /**
     * Gets the X-coordinate of this chunk
     *
     * @return X-coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the Z-coordinate of this chunk
     *
     * @return Z-coordinate
     */
    public int getZ() {
        return this.y;
    }

    /**
     * Gets the world containing this chunk
     *
     * @return Parent World
     */
    @NotNull
    public World getWorld() {
        return this.world;
    }

    /**
     * Gets a block from this chunk
     *
     * @param x 0-15
     * @param y 0-255
     * @param z 0-15
     * @return the Block
     */
    @NotNull
    public Block getBlock(int x, int y, int z) {
        // TODO implement when blocks are added
        return null;
    }

    /**
     * Capture thread-safe read-only snapshot of chunk data
     *
     * @return ChunkSnapshot
     */
    @NotNull
    public ChunkSnapshot getChunkSnapshot() {
        // TODO implement when chunk snapshot is added
        return null;
    }

    /**
     * Capture thread-safe read-only snapshot of chunk data
     *
     * @param includeMaxblocky - if true, snapshot includes per-coordinate
     *     maximum Y values
     * @param includeBiome - if true, snapshot includes per-coordinate biome
     *     type
     * @param includeBiomeTempRain - if true, snapshot includes per-coordinate
     *     raw biome temperature and rainfall
     * @return ChunkSnapshot
     */
    @NotNull
    public ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain) {
        // TODO implement when chunk snapshot is added
        return null;
    }

    /**
     * Checks if entities in this chunk are loaded.
     *
     * @return True if entities are loaded.
     */
    public boolean isEntitiesLoaded() {
        // TODO figure out this value
        return false;
    }

    /**
     * Get a list of all entities in the chunk.
     * This will force load any entities, which are not loaded.
     *
     * @return The entities.
     */
    @NotNull
    public Entity[] getEntities() {
        // TODO implement when entities are added
        return null;
    }

    /**
     * Get a list of all tile entities in the chunk.
     *
     * @return The tile entities.
     */
    @NotNull
    public BlockState[] getTileEntities() {
        // Implement when block state is added
        return null;
    }

    /**
     * Checks if the chunk is loaded.
     *
     * @return True if it is loaded.
     */
    public boolean isLoaded() {
        // TODO figure out if this chunk is loaded
        return false;
    }

    /**
     * Loads the chunk.
     *
     * @param generate Whether or not to generate a chunk if it doesn't
     *     already exist
     * @return true if the chunk has loaded successfully, otherwise false
     */
    public boolean load(boolean generate) {
        // TODO generate chunk
        return false;
    }

    /**
     * Loads the chunk.
     *
     * @return true if the chunk has loaded successfully, otherwise false
     */
    public boolean load() {
        return this.load(true);
    }

    /**
     * Unloads and optionally saves the Chunk
     *
     * @param save Controls whether the chunk is saved
     * @return true if the chunk has unloaded successfully, otherwise false
     */
    public boolean unload(boolean save) {
        // TODO save chunk
        return false;
    }

    /**
     * Unloads and optionally saves the Chunk
     *
     * @return true if the chunk has unloaded successfully, otherwise false
     */
    public boolean unload() {
        return this.unload(true);
    }

    /**
     * Checks if this chunk can spawn slimes without being a swamp biome.
     *
     * @return true if slimes are able to spawn in this chunk
     */
    public boolean isSlimeChunk() {
        // TODO check if this chunk is a slime chunk
        return false;
    }

    /**
     * Gets whether the chunk at the specified chunk coordinates is force
     * loaded.
     * <p>
     * A force loaded chunk will not be unloaded due to lack of player activity.
     *
     * @return force load status
     * @see World#isChunkForceLoaded(int, int)
     */
    public boolean isForceLoaded() {
        // TODO check if this chunk is force loaded after world gets added
        return false;
    }

    /**
     * Sets whether the chunk at the specified chunk coordinates is force
     * loaded.
     * <p>
     * A force loaded chunk will not be unloaded due to lack of player activity.
     *
     * @param forced force load status
     * @see World#setChunkForceLoaded(int, int, boolean)
     */
    public void setForceLoaded(boolean forced) {
        // TODO set this chunk to be force loaded after world gets added

    }

    /**
     * Adds a plugin ticket for this chunk, loading this chunk if it is not
     * already loaded.
     * <p>
     * A plugin ticket will prevent a chunk from unloading until it is
     * explicitly removed. A plugin instance may only have one ticket per chunk,
     * but each chunk can have multiple plugin tickets.
     * </p>
     *
     * @param plugin Plugin which owns the ticket
     * @return {@code true} if a plugin ticket was added, {@code false} if the
     * ticket already exists for the plugin
     * @throws IllegalStateException If the specified plugin is not enabled
     * @see World#addPluginChunkTicket(int, int, Plugin)
     */
    public boolean addPluginChunkTicket(@NotNull Plugin plugin) {
        // TODO add a plugin ticket once plugins are added
        return false;
    }

    /**
     * Removes the specified plugin's ticket for this chunk
     * <p>
     * A plugin ticket will prevent a chunk from unloading until it is
     * explicitly removed. A plugin instance may only have one ticket per chunk,
     * but each chunk can have multiple plugin tickets.
     * </p>
     *
     * @param plugin Plugin which owns the ticket
     * @return {@code true} if the plugin ticket was removed, {@code false} if
     * there is no plugin ticket for the chunk
     * @see World#removePluginChunkTicket(int, int, Plugin)
     */
    public boolean removePluginChunkTicket(@NotNull Plugin plugin) {
        // TODO remove a plugin ticket once the plugins are added
        return false;
    }

    /**
     * Retrieves a collection specifying which plugins have tickets for this
     * chunk. This collection is not updated when plugin tickets are added or
     * removed to this chunk.
     * <p>
     * A plugin ticket will prevent a chunk from unloading until it is
     * explicitly removed. A plugin instance may only have one ticket per chunk,
     * but each chunk can have multiple plugin tickets.
     * </p>
     *
     * @return unmodifiable collection containing which plugins have tickets for
     * this chunk
     * @see World#getPluginChunkTickets(int, int)
     */
    @NotNull
    public Collection<Plugin> getPluginChunkTickets() {
        // TODO return all tickets once plugins are added
        return null;
    }

    /**
     * Gets the amount of time in ticks that this chunk has been inhabited.
     *
     * Note that the time is incremented once per tick per player in the chunk.
     *
     * @return inhabited time
     */
    public long getInhabitedTime() {
        // TODO figure out how to get inhabited time from the server
        return 0;
    }

    /**
     * Sets the amount of time in ticks that this chunk has been inhabited.
     *
     * @param ticks new inhabited time
     */
    public void setInhabitedTime(long ticks) {
        // TODO figure out how to set the inhabited time
    }

    /**
     * Tests if this chunk contains the specified block.
     *
     * @param block block to test
     * @return if the block is contained within
     */
    public boolean contains(@NotNull BlockData block) {
        return false; // Implement when block data is added
    }
}
