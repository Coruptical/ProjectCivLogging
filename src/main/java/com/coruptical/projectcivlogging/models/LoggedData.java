package com.coruptical.projectcivlogging.models;

import com.coruptical.projectcivlogging.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LoggedData {
    private String storedData;
    private LogType logType;

    // Blocks placed/broken by players
    public LoggedData(Player p, Block b, LogType logType){
        this.logType = logType;
        String date = Utils.getNow();
        String pName = p.getName();
        String worldLocation = Utils.formatWorldLocation(b.getLocation());
        String blockMaterial = b.getType().toString();

        storedData = String.format("%s;%s;%s;%s",
                date, pName, worldLocation, blockMaterial);
    }

    // Blocks broken by explosions
    public LoggedData(Entity e){
        logType = LogType.BLOCK_BROKEN_EXPLOSION;
        String date = Utils.getNow();
        String typeEntity = e.getType().toString();
        String worldLocation = Utils.formatWorldLocation(e.getLocation());

        storedData = String.format("%s;%s;%s", date, typeEntity, worldLocation);
    }


    // Entities killed by players
    public LoggedData(Entity e, Player p){
        logType = LogType.PLAYER_KILLED_ENTITY;
        String date = Utils.getNow();
        String typeEntity = e.getType().toString();
        String pName = p.getName();
        String eLocation = Utils.formatWorldLocation(e.getLocation());

        storedData = String.format("%s;%s;%s;%s",
                date, typeEntity, pName, eLocation);
    }

    // Players killed by players
    public LoggedData(Player victim, Player attacker){
        logType = LogType.PLAYER_KILLED_PLAYER;
        String date = Utils.getNow();
        String victimName = victim.getName();
        String attackerName = attacker.getName();
        String victimLocation = Utils.formatWorldLocation(victim.getLocation());

        storedData = String.format("%s;%s;%s;%s",
                date, victimName, attackerName, victimLocation);
    }

    // Inventory edits made by players
    public LoggedData(Player p, Block b){
        logType = LogType.PLAYER_EDITED_INVENTORY;
        String date = Utils.getNow();
        String pName = p.getName();
        String inventoryBlockType = b.getType().toString();
        String inventoryLocation = Utils.formatWorldLocation(b.getLocation());

        storedData = String.format("%s;%s;%s;%s",
                date, pName, inventoryBlockType, inventoryLocation);
    }

    public String format(){
        StringBuilder builder = new StringBuilder();
        String formatted = "";
        String[] storedDataArray = storedData.split(";");

        ArrayList<Object> unformattedInfo = Utils.getInstance().getLogTypeFormatMap().get(this.logType);
        String unformatted = (String) unformattedInfo.get(0);

        // Scuffed casting here but it's fine lol
        List<Integer> order = (List<Integer>) unformattedInfo.get(1);

        String[] unformattedArray = unformatted.split(";");
        builder.append(unformattedArray[0]);
        for (int i = 0; i < storedDataArray.length; i++){
            builder.append(String.format(unformattedArray[i + 1], storedDataArray[order.get(i)]));
        }
        formatted = builder.toString();
        return formatted;
    }
}
