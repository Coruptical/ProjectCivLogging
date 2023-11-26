package com.coruptical.projectcivlogging.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager {
    private File workingDirectory;

    private final String dirName;
    private final String workingDirName;
    private final JavaPlugin plugin;

    private final String workingDirectoryPath;
    private final String dirPath;


    public FileManager(JavaPlugin plugin, String dirName, String workingDirName){
        this.plugin = plugin;
        this.dirName = dirName;
        this.workingDirName = workingDirName;

        workingDirectoryPath = plugin.getDataFolder().getAbsolutePath() +
                "\\" + dirName + "\\" + workingDirName;
        dirPath = plugin.getDataFolder().getAbsolutePath() + "\\" + dirName;
    }

    private void createDirectory(){
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public void createWorkingDirectory(){
        File workingDir = new File(workingDirectoryPath);
        if(!workingDir.exists()){
            workingDir.mkdir();
        }
    }
    
    public void init(){
        createDirectory();
        createWorkingDirectory();
    }

    public void writeLog(String fileName, String toWrite){
        try{
            File file = new File(workingDirectoryPath +
                    "\\" + fileName + ".log");

            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (IOException e){
            plugin.getLogger().log(Level.SEVERE, "Could not write()");
        }
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public String getDirName() {
        return dirName;
    }

}
