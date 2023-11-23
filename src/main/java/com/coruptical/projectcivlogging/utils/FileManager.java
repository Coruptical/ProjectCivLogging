package com.coruptical.projectcivlogging.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager {
    private String dirName;
    private String fileName;
    private JavaPlugin plugin;

    public FileManager(JavaPlugin plugin, String fileName, String dirName){
        this.fileName = fileName + ".log";
        this.plugin = plugin;
        this.dirName = dirName;
    }

    private void createDirectory(){
        File dir = new File(plugin.getDataFolder().getAbsolutePath() +
                "\\" + dirName);
        if(!dir.exists()){
            dir.mkdir();
        }
    }
    
    public void init(){
        createDirectory();

        String filePath = plugin.getDataFolder().getAbsolutePath() +
                "\\" + dirName + "\\" + fileName;

        File file = new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE,
                        "Failed to create " + fileName);
                throw new RuntimeException(e);
            }
        }

    }

    public String getFileName() {
        return fileName;
    }

    public String getDirName() {
        return dirName;
    }
}
