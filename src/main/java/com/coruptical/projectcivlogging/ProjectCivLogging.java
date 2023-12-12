package com.coruptical.projectcivlogging;

import com.coruptical.projectcivlogging.commands.PlaytimeCommand;
import com.coruptical.projectcivlogging.commands.ReloadConfigCommand;
import com.coruptical.projectcivlogging.event.*;
import com.coruptical.projectcivlogging.models.LogType;
import com.coruptical.projectcivlogging.models.LoggedData;
import com.coruptical.projectcivlogging.utils.PlaytimeHandler;
import com.coruptical.projectcivlogging.utils.TimedLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;

public final class ProjectCivLogging extends JavaPlugin {
    private static ProjectCivLogging instance;

    private final HashMap<LogType, Stack<LoggedData>> loggedDataMap = new HashMap<>();

    private final HashMap<Player, ItemStack[]> inventoryContentsMap = new HashMap<>();

    private final HashMap<Player, Integer> playtimeMap = new HashMap<>();

    private PlaytimeHandler playtimeHandler;

    public PlaytimeHandler getPlaytimeHandler() {
        return playtimeHandler;
    }

    public HashMap<Player, Integer> getPlaytimeMap() {
        return playtimeMap;
    }

    public HashMap<Player, ItemStack[]> getInventoryContentsMap() {
        return inventoryContentsMap;
    }

    public HashMap<LogType, Stack<LoggedData>> getLoggedDataMap() {
        return loggedDataMap;
    }

    public static ProjectCivLogging getInstance() {
        return instance;
    }

    private TimedLogger timedLogger;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        timedLogger = TimedLogger.getInstance();
        timedLogger.init(instance);

        // Create empty stacks for all log types
        initLoggedDataMap();

        // Start logging
        timedLogger.start();

        // Register listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new EntityKilledListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryEditListener(), this);
        getServer().getPluginManager().registerEvents(new PlaytimeListener(), this);

        // Register commands
        getCommand("civlogging_reload").setExecutor(new ReloadConfigCommand());

        PlaytimeCommand playtimeCommand = new PlaytimeCommand();
        getCommand("playtime").setExecutor(playtimeCommand);
        getCommand("playtime").setTabCompleter(playtimeCommand);

        getLogger().log(Level.INFO, "Logging interval set to "
                + getLoggingIntervalInTicks() + " ticks.");


        // Get playtime handler.
        playtimeHandler = PlaytimeHandler.getInstance();
        playtimeHandler.init(this);

        playtimeHandler.startTiming();
    }

    private void initLoggedDataMap(){
        loggedDataMap.put(LogType.BLOCK_BROKEN_PLAYER, new Stack<>());
        loggedDataMap.put(LogType.BLOCK_BROKEN_EXPLOSION, new Stack<>());
        loggedDataMap.put(LogType.BLOCK_PLACED, new Stack<>());
        loggedDataMap.put(LogType.PLAYER_KILLED_ENTITY, new Stack<>());
        loggedDataMap.put(LogType.PLAYER_KILLED_PLAYER, new Stack<>());
        loggedDataMap.put(LogType.PLAYER_EDITED_INVENTORY, new Stack<>());
    }

    @Override
    public void onDisable() {
        timedLogger.saveOnce();

        for (Player player: getServer().getOnlinePlayers()){
            playtimeHandler.saveOne(player);
        }
    }

    public String getAdminPermission(){
        return getConfig().getString("Admin_Permission");
    }

    public String getNoPermissionMessage(){
        return ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("No_Permission_Message"));
    }

    public long getLoggingIntervalInTicks(){
        // Set the default interval to 10m (12,000 ticks)
        long interval = 12000;
        String configInterval = getConfig().getString("Logging_Interval");
        String timeType = configInterval.substring(configInterval.length() - 1);

        long timeTicks = 0;
        if (timeType.equalsIgnoreCase("s")){
            timeTicks = Long.parseLong(configInterval.replace("s", "")) * 20;
        }else if(timeType.equalsIgnoreCase("m")){
            timeTicks = Long.parseLong(configInterval.replace("m", "")) * 60 * 20;
        }else if(timeType.equalsIgnoreCase("h")){
            timeTicks = Long.parseLong(configInterval.replace("h", "")) * 60 * 60 * 20;
        }

        if(timeTicks > 0){
            interval = timeTicks;
        }
        return interval;
    }
}
