package com.coruptical.projectcivlogging.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager {
    private File file;

    private final String dirName;
    private final String fileName;
    private final JavaPlugin plugin;

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

    public void write(boolean append, String toWrite){
        try{
            FileWriter fileWriter = new FileWriter(this.file, append);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(toWrite);
            if (append) bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e){
            plugin.getLogger().log(Level.SEVERE, "Could not write()");
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
