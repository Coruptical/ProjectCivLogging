package com.coruptical.projectcivlogging.event;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlaytimeListener implements Listener {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        int playtime = plugin.getPlaytimeHandler().getConfig().getInt(uuid);

        plugin.getPlaytimeMap().put(p, playtime);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        plugin.getPlaytimeHandler().saveOne(p);
    }
}
