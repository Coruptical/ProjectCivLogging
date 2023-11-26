package com.coruptical.projectcivlogging.utils;

import org.bukkit.Location;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class Utils {
    public static String getNow(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.m.ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String formatWorldLocation(Location l){
        return "[" + l.getWorld().getName() + "]: " +
                l.getX() + " " + l.getY() + " " + l.getZ();
    }
}
