package com.coruptical.projectcivlogging.models;

import com.coruptical.projectcivlogging.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


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
}
