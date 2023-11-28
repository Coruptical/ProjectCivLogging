package com.coruptical.projectcivlogging.utils;

import com.coruptical.projectcivlogging.models.LogType;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Utils {
    private final HashMap<LogType, ArrayList<Object>> logTypeFormatMap = new HashMap<>();
    private static final Utils i = new Utils();

    public static Utils getInstance() {
        return i;
    }

    private Utils(){
        ArrayList<Object> blockBrokenPlayer = new ArrayList<>();
        blockBrokenPlayer.add("[;%s]: ;%s broke ;%s at: ;%s");
        blockBrokenPlayer.add(Arrays.asList(0, 1, 3, 2));

        ArrayList<Object> blockBrokenExplosion = new ArrayList<>();
        blockBrokenPlayer.add("[;%s]: ;%s broke blocks by explosion at: ;%s");
        blockBrokenPlayer.add(Arrays.asList(0, 1, 2));

        ArrayList<Object> blocksPlaced = new ArrayList<>();
        blocksPlaced.add("[;%s]: ;%s placed ;%s at: ;%s");
        blocksPlaced.add(Arrays.asList(0, 1, 3, 2));

        ArrayList<Object> playerKilledEntity = new ArrayList<>();
        playerKilledEntity.add("[;%s]: ;%s killed ;%s at: ;%s");
        playerKilledEntity.add(Arrays.asList(0, 2, 1, 3));

        ArrayList<Object> playerKilledPlayer = new ArrayList<>();
        playerKilledPlayer.add("[;%s]: ;%s killed ;%s at: ;%s");
        playerKilledPlayer.add(Arrays.asList(0, 2, 1, 3));

        ArrayList<Object> playerEditedInventory = new ArrayList<>();
        playerEditedInventory.add("[;%s]: ;%s edited ;%s at: ;%s");
        playerEditedInventory.add(Arrays.asList(0, 1, 2, 3));


        logTypeFormatMap.put(LogType.BLOCK_BROKEN_PLAYER,
                blockBrokenPlayer);
        logTypeFormatMap.put(LogType.BLOCK_BROKEN_EXPLOSION,
                blockBrokenExplosion);
        logTypeFormatMap.put(LogType.BLOCK_PLACED,
                blocksPlaced);
        logTypeFormatMap.put(LogType.PLAYER_KILLED_ENTITY,
                playerKilledEntity);
        logTypeFormatMap.put(LogType.PLAYER_KILLED_PLAYER,
                playerKilledPlayer);
        logTypeFormatMap.put(LogType.PLAYER_EDITED_INVENTORY,
                playerEditedInventory);
    }

    public static String getNow(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.m.ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String formatWorldLocation(Location l){
        return "[" + l.getWorld().getName() + "]: " +
                l.getX() + " " + l.getY() + " " + l.getZ();
    }

    public HashMap<LogType, ArrayList<Object>> getLogTypeFormatMap() {
        return logTypeFormatMap;
    }
}
