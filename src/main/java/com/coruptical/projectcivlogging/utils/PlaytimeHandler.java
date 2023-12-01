package com.coruptical.projectcivlogging.utils;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class PlaytimeHandler {
    private final int SECONDS_PER_CYCLE = 3;
    private final int TICKS_PER_CYCLE = SECONDS_PER_CYCLE * 20;
    private final int CYCLES_BEFORE_SAVE = 10;
    private int currentCycle = 0;

    private File file;
    private FileConfiguration config;

    private ProjectCivLogging plugin;

    private static final PlaytimeHandler i = new PlaytimeHandler();

    private PlaytimeHandler(){

    }

    public FileConfiguration getConfig(){
        return this.config;
    }

    public static PlaytimeHandler getInstance() {
        return i;
    }

    public void init(ProjectCivLogging plugin){
        this.plugin = plugin;

        createConfig();


    }

    private void createConfig(){
        file = new File(plugin.getDataFolder(), "player_playtime.yml");

        if (!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource("player_playtime.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void startTiming(){
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : plugin.getPlaytimeMap().keySet()){
                    int playtime = plugin.getPlaytimeMap().get(p);
                    plugin.getPlaytimeMap().replace(p, playtime + SECONDS_PER_CYCLE);
                }

                if (++currentCycle == CYCLES_BEFORE_SAVE){
                    save();
                    currentCycle = 0;
                }
            }
        };
        bukkitRunnable.runTaskTimer(plugin, TICKS_PER_CYCLE, 60L);
    }

    public void saveOne(Player p ){
        int playtime = plugin.getPlaytimeMap().get(p);
        config.set(p.getUniqueId().toString(), playtime);

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(){
        for (Player p : plugin.getPlaytimeMap().keySet()){
            String uuid = p.getUniqueId().toString();
            int playtime = plugin.getPlaytimeMap().get(p);
            config.set(uuid, playtime);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}



