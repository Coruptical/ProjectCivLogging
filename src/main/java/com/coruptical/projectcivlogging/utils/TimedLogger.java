package com.coruptical.projectcivlogging.utils;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import org.bukkit.scheduler.BukkitRunnable;

public class TimedLogger {
    private ProjectCivLogging plugin;
    private static final TimedLogger instance = new TimedLogger();
    private final String DIRECTORY_NAME = "logs";

    private FileManager blocksBrokenPlayer;
    private FileManager blocksBrokenExplosion;
    private FileManager blocksPlaced;
    private FileManager lavaAndFlintsteel;
    private FileManager entityKilledPlayer;
    private FileManager playerKilledPlayer;
    private FileManager chestEditsMade;

    private BukkitRunnable runnable;

    private TimedLogger(){
    }

    public static TimedLogger getInstance() {
        return instance;
    }

    public void init(ProjectCivLogging plugin){
        // Instantiate all variables that we need to manage logs later.
        // This method should be called before any other method.
        this.plugin = plugin;

        blocksBrokenPlayer = new FileManager(this.plugin, "blocks_broken_by_player", DIRECTORY_NAME);
        blocksBrokenExplosion = new FileManager(this.plugin, "blocks_broken_by_explosion", DIRECTORY_NAME);
        blocksPlaced = new FileManager(this.plugin, "blocks_placed", DIRECTORY_NAME);
        lavaAndFlintsteel = new FileManager(this.plugin, "lava_and_flintsteel_uses", DIRECTORY_NAME);
        entityKilledPlayer = new FileManager(this.plugin, "player_killed_entity", DIRECTORY_NAME);
        playerKilledPlayer = new FileManager(this.plugin, "player_killed_player", DIRECTORY_NAME);
        chestEditsMade = new FileManager(this.plugin, "placed_inventory_edits", DIRECTORY_NAME);
    }

    public void run(){
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
        runnable.runTaskTimerAsynchronously(plugin, 1L, plugin.getLoggingIntervalInTicks());
    }

    public void cancelRunnable(){
        runnable.cancel();
    }
}
