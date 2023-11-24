package com.coruptical.projectcivlogging.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager {
    private File file;

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

        file = new File(filePath);
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

    public void append(String toWrite){
        try {
            FileWriter writer = new FileWriter(this.file, true);
            BufferedWriter bwriter = new BufferedWriter(writer);
            bwriter.write(toWrite);
            bwriter.newLine();
            bwriter.close();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not instantiate write object.");
            throw new RuntimeException(e);
        }
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDirName() {
        return dirName;
    }
}
