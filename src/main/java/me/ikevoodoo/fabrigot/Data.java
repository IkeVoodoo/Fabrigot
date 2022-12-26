package me.ikevoodoo.fabrigot;

import me.ikevoodoo.fabrigot.api.FabrigotServer;
import net.minecraft.MinecraftVersion;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

import java.io.File;
import java.nio.file.Path;

public class Data {

    /**
     The minecraft server instance
     */
    public static MinecraftDedicatedServer SERVER = null;
    public static FabrigotServer FABRIGOT_SERVER = null;

    /**
     The server working directory
     */
    public static File SERVER_HOME = null;
}
