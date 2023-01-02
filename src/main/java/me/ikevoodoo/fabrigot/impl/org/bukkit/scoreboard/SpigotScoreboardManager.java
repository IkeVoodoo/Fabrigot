package me.ikevoodoo.fabrigot.impl.org.bukkit.scoreboard;

import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpigotScoreboardManager implements ScoreboardManager {

    private final SpigotScoreboard mainScoreboard;
    private final MinecraftServer server;
    private final List<Scoreboard> scoreboards;

    public SpigotScoreboardManager(MinecraftServer server) {
        this.mainScoreboard = new SpigotScoreboard(server.getScoreboard());
        this.server = server;

        this.scoreboards = new ArrayList<>();
    }

    @Override
    public @NotNull Scoreboard getMainScoreboard() {
        return this.mainScoreboard;
    }

    @Override
    public @NotNull Scoreboard getNewScoreboard() {
        var board = new SpigotScoreboard(new ServerScoreboard(this.server));
        this.scoreboards.add(board);
        return board;
    }
}
