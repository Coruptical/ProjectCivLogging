package com.coruptical.projectcivlogging;

import com.coruptical.projectcivlogging.utils.TimedLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;

public final class ProjectCivLogging extends JavaPlugin {
    private static ProjectCivLogging instance;

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
    }

    @Override
    public void onDisable() {

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
