package com.coruptical.projectcivlogging.utils;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import com.coruptical.projectcivlogging.models.LogType;
import com.coruptical.projectcivlogging.models.LoggedData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;

public class TimedLogger {
    private ProjectCivLogging plugin;
    private static final TimedLogger instance = new TimedLogger();
    private final String DIRECTORY_NAME = "logs";
    private final HashMap<LogType, FileManager> logTypeManagerMap = new HashMap<>();


    private FileManager blocksBrokenPlayer;
    private FileManager blocksBrokenExplosion;
    private FileManager blocksPlaced;
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

        // Create file managers
        blocksBrokenPlayer = new FileManager(this.plugin, DIRECTORY_NAME, "blocks_broken_by_player");
        blocksBrokenExplosion = new FileManager(this.plugin, DIRECTORY_NAME, "blocks_broken_by_explosion");
        blocksPlaced = new FileManager(this.plugin, DIRECTORY_NAME, "blocks_placed");
        entityKilledPlayer = new FileManager(this.plugin, DIRECTORY_NAME, "player_killed_entity");
        playerKilledPlayer = new FileManager(this.plugin, DIRECTORY_NAME, "player_killed_player");
        chestEditsMade = new FileManager(this.plugin, DIRECTORY_NAME, "placed_inventory_edits");

        // Initialise file managers
        blocksBrokenPlayer.init();
        blocksBrokenExplosion.init();
        blocksPlaced.init();
        entityKilledPlayer.init();
        playerKilledPlayer.init();
        chestEditsMade.init();

        // Assort log types to their file manager
        logTypeManagerMap.put(LogType.BLOCK_BROKEN_PLAYER, blocksBrokenPlayer);
        logTypeManagerMap.put(LogType.BLOCK_BROKEN_EXPLOSION, blocksBrokenExplosion);
        logTypeManagerMap.put(LogType.BLOCK_PLACED, blocksPlaced);
        logTypeManagerMap.put(LogType.PLAYER_KILLED_ENTITY, entityKilledPlayer);
        logTypeManagerMap.put(LogType.PLAYER_KILLED_PLAYER, playerKilledPlayer);
        logTypeManagerMap.put(LogType.PLAYER_EDITED_INVENTORY, chestEditsMade);
    }

    public void start(){
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                long start = System.nanoTime();
                plugin.getLogger().log(Level.INFO, "Saving all stored logs...");

                String now = Utils.getNow();

                for (LogType logType : plugin.getLoggedDataMap().keySet()){
                    FileManager fileManager = logTypeManagerMap.get(logType);
                    Stack<LoggedData> logStack = plugin.getLoggedDataMap().get(logType);
                    if (logStack.empty()) continue;

                    StringBuilder builder = new StringBuilder();

                    while (!logStack.empty()){
                        LoggedData log = logStack.pop();
                        String formattedLog = log.format();
                        builder.append(formattedLog).append("\n");
                    }
                    fileManager.writeLog(now, builder.toString());
                }
                long elapsed = System.nanoTime() - start;
                plugin.getLogger().log(Level.INFO, "Successfully saved all stored logs in " + (double) elapsed/1000000 + "ms.");
            }
        };
        runnable.runTaskTimerAsynchronously(plugin, 1L, plugin.getLoggingIntervalInTicks());
    }

    public void stop(){
        runnable.cancel();
    }
}
