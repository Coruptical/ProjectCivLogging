package com.coruptical.projectcivlogging.event;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import com.coruptical.projectcivlogging.models.LogType;
import com.coruptical.projectcivlogging.models.LoggedData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EntityKilledListener implements Listener {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        Entity ent = e.getEntity();
        Player attacker = e.getEntity().getKiller();

        if (attacker == null) return;

        LoggedData log = new LoggedData(ent, attacker);
        plugin.getLoggedDataMap().get(LogType.PLAYER_KILLED_ENTITY).push(log);
        plugin.getServer().broadcastMessage("pushed new player killed entity onto stack.");

    }

    @EventHandler
    public void onPlayerKilled(PlayerDeathEvent e ) {
        Player victim = e.getEntity();
        Player attacker = e.getEntity().getKiller();

        if (attacker == null) return;

        LoggedData log = new LoggedData(victim, attacker);
        plugin.getLoggedDataMap().get(LogType.PLAYER_KILLED_PLAYER).push(log);
        plugin.getServer().broadcastMessage("pushed new player killed player onto stack.");
    }
}
