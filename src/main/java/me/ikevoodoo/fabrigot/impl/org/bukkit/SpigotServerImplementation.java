package me.ikevoodoo.fabrigot.impl.org.bukkit;

import me.ikevoodoo.fabrigot.Data;
import me.ikevoodoo.fabrigot.api.bans.lists.IpBanList;
import me.ikevoodoo.fabrigot.api.bans.lists.PlayerBanList;
import me.ikevoodoo.fabrigot.impl.org.bukkit.scheduler.SpigotScheduler;
import net.minecraft.loot.LootTable;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.*;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class SpigotServerImplementation implements Server {

    private final Logger logger;

    @SuppressWarnings("deprecation")
    private final UnsafeValues unsafeValues;
    private final PluginManager pluginManager;
    private final SimpleCommandMap commandMap;
    private final Thread serverThread;

    private final IpBanList ipBanList;
    private final PlayerBanList nameBanList;
    private final BukkitScheduler scheduler;
    private final Server.Spigot spigotImplementation;

    public SpigotServerImplementation(Thread serverThread) {
        this.logger = Logger.getLogger("Fabrigot | Spigot Server");
        this.unsafeValues = new SpigotUnsafeValuesImplementation();
        this.commandMap = new SimpleCommandMap(this);
        this.pluginManager = new SimplePluginManager(this, this.commandMap);
        this.serverThread = serverThread;

        this.ipBanList = new IpBanList(Data.SERVER.getPlayerManager().getIpBanList());
        this.nameBanList = new PlayerBanList(Data.SERVER.getPlayerManager().getUserBanList());

        this.scheduler = new SpigotScheduler();
        this.spigotImplementation = new SpigotImplementation(this);
    }

    @Override
    public @NotNull String getName() {
        return "Fabrigot - Fabric";
    }

    @Override
    public @NotNull String getVersion() {
        return Data.FABRIGOT_SERVER.getVersion();
    }

    @Override
    public @NotNull String getBukkitVersion() {
        return "E";
    }

    @Override
    public @NotNull Collection<? extends Player> getOnlinePlayers() {
        return Data.FABRIGOT_SERVER.getOnlinePlayers();
    }

    @Override
    public int getMaxPlayers() {
        return 0;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public int getViewDistance() {
        return 0;
    }

    @Override
    public @NotNull String getIp() {
        return null;
    }

    @Override
    public @NotNull String getWorldType() {
        return null;
    }

    @Override
    public boolean getGenerateStructures() {
        return false;
    }

    @Override
    public int getMaxWorldSize() {
        return 0;
    }

    @Override
    public boolean getAllowEnd() {
        return false;
    }

    @Override
    public boolean getAllowNether() {
        return false;
    }

    @Override
    public boolean hasWhitelist() {
        return false;
    }

    @Override
    public void setWhitelist(boolean value) {

    }

    @Override
    public boolean isWhitelistEnforced() {
        return false;
    }

    @Override
    public void setWhitelistEnforced(boolean value) {

    }

    @Override
    public @NotNull Set<OfflinePlayer> getWhitelistedPlayers() {
        return null;
    }

    @Override
    public void reloadWhitelist() {

    }

    @Override
    public int broadcastMessage(@NotNull String message) {
        this.getOnlinePlayers().forEach(player -> player.sendMessage(message));
        return 0;
    }

    @Override
    public @NotNull String getUpdateFolder() {
        return "update";
    }

    @Override
    public @NotNull File getUpdateFolderFile() {
        return new File(Data.SERVER_HOME, "plugins/update");
    }

    @Override
    public long getConnectionThrottle() {
        return 0;
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerWaterSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerWaterAmbientSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerWaterUndergroundCreatureSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerAmbientSpawns() {
        return 0;
    }

    @Override
    public @Nullable Player getPlayer(@NotNull String name) {
        return null;
    }

    @Override
    public @Nullable Player getPlayerExact(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull List<Player> matchPlayer(@NotNull String name) {
        return null;
    }

    @Override
    public @Nullable Player getPlayer(@NotNull UUID id) {
        //return Data.FABRIGOT_SERVER.getSpigotPlayer(id);
        return null;
    }

    @Override
    public @NotNull PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public @NotNull BukkitScheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public @NotNull ServicesManager getServicesManager() {
        return null;
    }

    @Override
    public @NotNull List<World> getWorlds() {
        return null;
    }

    @Override
    public @Nullable World createWorld(@NotNull WorldCreator creator) {
        return null;
    }

    @Override
    public boolean unloadWorld(@NotNull String name, boolean save) {
        return false;
    }

    @Override
    public boolean unloadWorld(@NotNull World world, boolean save) {
        return false;
    }

    @Override
    public @Nullable World getWorld(@NotNull String name) {
        return null;
    }

    @Override
    public @Nullable World getWorld(@NotNull UUID uid) {
        return null;
    }

    @Override
    public @Nullable MapView getMap(int id) {
        return null;
    }

    @Override
    public @NotNull MapView createMap(@NotNull World world) {
        return null;
    }

    @Override
    public @NotNull ItemStack createExplorerMap(@NotNull World world, @NotNull Location location, @NotNull StructureType structureType) {
        return null;
    }

    @Override
    public @NotNull ItemStack createExplorerMap(@NotNull World world, @NotNull Location location, @NotNull StructureType structureType, int radius, boolean findUnexplored) {
        return null;
    }

    @Override
    public void reload() {

    }

    @Override
    public void reloadData() {

    }

    @Override
    public @NotNull Logger getLogger() {
        return this.logger;
    }

    @Override
    public @Nullable PluginCommand getPluginCommand(@NotNull String name) {
        Command command = this.commandMap.getCommand(name);
        if (!(command instanceof PluginCommand pluginCommand)) return null;

        return pluginCommand;
    }

    @Override
    public void savePlayers() {

    }

    @Override
    public boolean dispatchCommand(@NotNull CommandSender sender, @NotNull String commandLine) throws CommandException {
        return this.commandMap.dispatch(sender, commandLine);
    }

    @Override
    public boolean addRecipe(@Nullable Recipe recipe) {
        return false;
    }

    @Override
    public @NotNull List<Recipe> getRecipesFor(@NotNull ItemStack result) {
        return null;
    }

    @Override
    public @Nullable Recipe getRecipe(@NotNull NamespacedKey recipeKey) {
        return null;
    }

    @Override
    public @Nullable Recipe getCraftingRecipe(@NotNull ItemStack[] craftingMatrix, @NotNull World world) {
        return null;
    }

    @Override
    public @NotNull ItemStack craftItem(@NotNull ItemStack[] craftingMatrix, @NotNull World world, @NotNull Player player) {
        return null;
    }

    @Override
    public @NotNull Iterator<Recipe> recipeIterator() {
        return null;
    }

    @Override
    public void clearRecipes() {

    }

    @Override
    public void resetRecipes() {

    }

    @Override
    public boolean removeRecipe(@NotNull NamespacedKey key) {
        return false;
    }

    @Override
    public @NotNull Map<String, String[]> getCommandAliases() {
        return null;
    }

    @Override
    public int getSpawnRadius() {
        return 0;
    }

    @Override
    public void setSpawnRadius(int value) {

    }

    @Override
    public boolean getOnlineMode() {
        return false;
    }

    @Override
    public boolean getAllowFlight() {
        return false;
    }

    @Override
    public boolean isHardcore() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public int broadcast(@NotNull String message, @NotNull String permission) {
        return 0;
    }

    @Override
    public @NotNull OfflinePlayer getOfflinePlayer(@NotNull String name) {
        return null;
    }

    @Override
    public @NotNull OfflinePlayer getOfflinePlayer(@NotNull UUID id) {
        return null;
    }

    @Override
    public @NotNull Set<String> getIPBans() {
        return null;
    }

    @Override
    public void banIP(@NotNull String address) {

    }

    @Override
    public void unbanIP(@NotNull String address) {

    }

    @Override
    public @NotNull Set<OfflinePlayer> getBannedPlayers() {
        return null;
    }

    @Override
    public @NotNull BanList getBanList(BanList.@NotNull Type type) {
        return type == BanList.Type.IP ? this.ipBanList : this.nameBanList;
    }

    @Override
    public @NotNull Set<OfflinePlayer> getOperators() {
        return null;
    }

    @Override
    public @NotNull GameMode getDefaultGameMode() {
        return null;
    }

    @Override
    public void setDefaultGameMode(@NotNull GameMode mode) {

    }

    @Override
    public @NotNull ConsoleCommandSender getConsoleSender() {
        return null;
    }

    @Override
    public @NotNull File getWorldContainer() {
        return Data.SERVER_HOME;
    }

    @Override
    public @NotNull OfflinePlayer[] getOfflinePlayers() {
        return new OfflinePlayer[0];
    }

    @Override
    public @NotNull Messenger getMessenger() {
        return null;
    }

    @Override
    public @NotNull HelpMap getHelpMap() {
        return null;
    }

    @Override
    public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type) {
        return null;
    }

    @Override
    public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type, @NotNull String title) {
        return null;
    }

    @Override
    public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, int size) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Inventory createInventory(@Nullable InventoryHolder owner, int size, @NotNull String title) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Merchant createMerchant(@Nullable String title) {
        return null;
    }

    @Override
    public int getMonsterSpawnLimit() {
        return 0;
    }

    @Override
    public int getAnimalSpawnLimit() {
        return 0;
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        return 0;
    }

    @Override
    public int getWaterAmbientSpawnLimit() {
        return 0;
    }

    @Override
    public int getWaterUndergroundCreatureSpawnLimit() {
        return 0;
    }

    @Override
    public int getAmbientSpawnLimit() {
        return 0;
    }

    @Override
    public boolean isPrimaryThread() {
        return Thread.currentThread() == this.serverThread;
    }

    @Override
    public @NotNull String getMotd() {
        return null;
    }

    @Override
    public @Nullable String getShutdownMessage() {
        return null;
    }

    @Override
    public @NotNull Warning.WarningState getWarningState() {
        return null;
    }

    @Override
    public @NotNull ItemFactory getItemFactory() {
        return null;
    }

    @Override
    public @Nullable ScoreboardManager getScoreboardManager() {
        return null;
    }

    @Override
    public @Nullable CachedServerIcon getServerIcon() {
        return null;
    }

    @Override
    public @NotNull CachedServerIcon loadServerIcon(@NotNull File file) throws IllegalArgumentException, Exception {
        return null;
    }

    @Override
    public @NotNull CachedServerIcon loadServerIcon(@NotNull BufferedImage image) throws IllegalArgumentException, Exception {
        return null;
    }

    @Override
    public void setIdleTimeout(int threshold) {

    }

    @Override
    public int getIdleTimeout() {
        return 0;
    }

    @NotNull
    @Override
    public ChunkGenerator.ChunkData createChunkData(@NotNull World world) {
        return null;
    }

    @Override
    public @NotNull BossBar createBossBar(@Nullable String title, @NotNull BarColor color, @NotNull BarStyle style, @NotNull BarFlag... flags) {
        return null;
    }

    @Override
    public @NotNull KeyedBossBar createBossBar(@NotNull NamespacedKey key, @Nullable String title, @NotNull BarColor color, @NotNull BarStyle style, @NotNull BarFlag... flags) {
        return null;
    }

    @Override
    public @NotNull Iterator<KeyedBossBar> getBossBars() {
        return null;
    }

    @Override
    public @Nullable KeyedBossBar getBossBar(@NotNull NamespacedKey key) {
        return null;
    }

    @Override
    public boolean removeBossBar(@NotNull NamespacedKey key) {
        return false;
    }

    @Override
    public @Nullable Entity getEntity(@NotNull UUID uuid) {
        return null;
    }

    @Override
    public @Nullable Advancement getAdvancement(@NotNull NamespacedKey key) {
        return null;
    }

    @Override
    public @NotNull Iterator<Advancement> advancementIterator() {
        return null;
    }

    @Override
    public @NotNull BlockData createBlockData(@NotNull Material material) {
        return null;
    }

    @Override
    public @NotNull BlockData createBlockData(@NotNull Material material, @Nullable Consumer<BlockData> consumer) {
        return null;
    }

    @Override
    public @NotNull BlockData createBlockData(@NotNull String data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull BlockData createBlockData(@Nullable Material material, @Nullable String data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @Nullable <T extends Keyed> Tag<T> getTag(@NotNull String registry, @NotNull NamespacedKey tag, @NotNull Class<T> clazz) {
        return null;
    }

    @Override
    public @NotNull <T extends Keyed> Iterable<Tag<T>> getTags(@NotNull String registry, @NotNull Class<T> clazz) {
        return null;
    }

    @Override
    public @Nullable LootTable getLootTable(@NotNull NamespacedKey key) {
        return null;
    }

    @Override
    public @NotNull List<Entity> selectEntities(@NotNull CommandSender sender, @NotNull String selector) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull StructureManager getStructureManager() {
        return null;
    }

    @Override
    public @NotNull UnsafeValues getUnsafe() {
        return this.unsafeValues;
    }

    @Override
    public @NotNull Spigot spigot() {
        return this.spigotImplementation;
    }

    @Override
    public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, @NotNull byte[] message) {

    }

    @Override
    public @NotNull Set<String> getListeningPluginChannels() {
        return null;
    }
}
