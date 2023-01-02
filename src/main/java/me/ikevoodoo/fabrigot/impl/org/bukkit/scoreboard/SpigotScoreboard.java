package me.ikevoodoo.fabrigot.impl.org.bukkit.scoreboard;

import me.ikevoodoo.fabrigot.Fabrigot;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SpigotScoreboard implements Scoreboard {

    private final ServerScoreboard scoreboard;

    public SpigotScoreboard(ServerScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public @NotNull Objective registerNewObjective(@NotNull String name, @NotNull String criteria) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Objective registerNewObjective(@NotNull String name, @NotNull String criteria, @NotNull String displayName) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Objective registerNewObjective(@NotNull String name, @NotNull String criteria, @NotNull String displayName, @NotNull RenderType renderType) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @Nullable Objective getObjective(@NotNull String name) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Set<Objective> getObjectivesByCriteria(@NotNull String criteria) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Set<Objective> getObjectives() {
        return null;
    }

    @Override
    public @Nullable Objective getObjective(@NotNull DisplaySlot slot) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Set<Score> getScores(@NotNull OfflinePlayer player) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Set<Score> getScores(@NotNull String entry) throws IllegalArgumentException {
        return null;
    }

    @Override
    public void resetScores(@NotNull OfflinePlayer player) throws IllegalArgumentException {

    }

    @Override
    public void resetScores(@NotNull String entry) throws IllegalArgumentException {

    }

    @Override
    public @Nullable Team getPlayerTeam(@NotNull OfflinePlayer player) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @Nullable Team getEntryTeam(@NotNull String entry) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @Nullable Team getTeam(@NotNull String teamName) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Set<Team> getTeams() {
        return null;
    }

    @Override
    public @NotNull Team registerNewTeam(@NotNull String name) throws IllegalArgumentException {
        return null;
    }

    @Override
    public @NotNull Set<OfflinePlayer> getPlayers() {
        return null;
    }

    @Override
    public @NotNull Set<String> getEntries() {
        return null;
    }

    @Override
    public void clearSlot(@NotNull DisplaySlot slot) throws IllegalArgumentException {

    }
}
