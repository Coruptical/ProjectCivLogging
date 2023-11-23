package com.coruptical.projectcivlogging;

import org.bukkit.plugin.java.JavaPlugin;

public final class ProjectCivLogging extends JavaPlugin {
    private static ProjectCivLogging instance;

    public static ProjectCivLogging getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }
}
