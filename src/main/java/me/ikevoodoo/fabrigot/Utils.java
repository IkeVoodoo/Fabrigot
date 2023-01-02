package me.ikevoodoo.fabrigot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.io.File;
import java.util.UUID;

public class Utils {

    private Utils() {

    }

    public static ServerPlayerEntity getPlayer(String name) {
        try {
            return Fabrigot.getMinecraftServer().getPlayerManager().getPlayer(UUID.fromString(name));
        } catch (Exception e) {
            return Fabrigot.getMinecraftServer().getPlayerManager().getPlayer(name);
        }
    }

    public static File ensureDirectory(File parent, String child) {
        File file = new File(parent, child);
        if (!file.exists()) {
            file.mkdirs();
        }

        if (file.exists() && !file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
        return file;
    }

    public static String getPlain(Text text) {
        if (text == null) return null;

        return text.getString();
    }

    public static PlayerBedEnterEvent.BedEnterResult fromSleepFailureReason(PlayerEntity.SleepFailureReason reason) {
        if (reason == null) return PlayerBedEnterEvent.BedEnterResult.OK;
        return switch(reason) {
            case NOT_SAFE -> PlayerBedEnterEvent.BedEnterResult.NOT_SAFE;
            case NOT_POSSIBLE_HERE -> PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_HERE;
            case TOO_FAR_AWAY -> PlayerBedEnterEvent.BedEnterResult.TOO_FAR_AWAY;
            case NOT_POSSIBLE_NOW -> PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_NOW;
            case OTHER_PROBLEM, OBSTRUCTED -> PlayerBedEnterEvent.BedEnterResult.OTHER_PROBLEM;
        };
    }

    public static PlayerEntity.SleepFailureReason toSleepFailureReason(PlayerBedEnterEvent.BedEnterResult result) {
        return switch(result) {
            case OK -> null;
            case NOT_SAFE -> PlayerEntity.SleepFailureReason.NOT_SAFE;
            case NOT_POSSIBLE_HERE -> PlayerEntity.SleepFailureReason.NOT_POSSIBLE_HERE;
            case TOO_FAR_AWAY -> PlayerEntity.SleepFailureReason.TOO_FAR_AWAY;
            case NOT_POSSIBLE_NOW -> PlayerEntity.SleepFailureReason.NOT_POSSIBLE_NOW;
            case OTHER_PROBLEM -> PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
        };
    }

}
