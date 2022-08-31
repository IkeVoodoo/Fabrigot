package me.ikevoodoo.fabrigot;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Items;

import java.io.File;

@Environment(EnvType.SERVER)
public class FabrigotServer implements DedicatedServerModInitializer {

    public static void ensureDataDirectory() {
        File file = new File(Data.SERVER.getRunDirectory(), "fabrigot");
        if (!file.exists()) {
            file.mkdirs();
        }

        if (file.exists() && !file.isDirectory()) {
            file.delete();
            file.mkdirs();
        }
    }

    @Override
    public void onInitializeServer() {

    }
}
