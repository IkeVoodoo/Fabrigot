package me.ikevoodoo.fabrigot;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;

import java.io.File;
import java.util.UUID;

public class Utils {

    private Utils() {

    }

    public static ServerPlayerEntity getPlayer(String name) {
        try {
            return Data.SERVER.getPlayerManager().getPlayer(UUID.fromString(name));
        } catch (Exception e) {
            return Data.SERVER.getPlayerManager().getPlayer(name);
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

}
