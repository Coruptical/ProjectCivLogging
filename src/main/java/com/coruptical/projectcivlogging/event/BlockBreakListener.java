package com.coruptical.projectcivlogging.event;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import com.coruptical.projectcivlogging.models.LogType;
import com.coruptical.projectcivlogging.models.LoggedData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockBreakListener implements Listener {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();

        LoggedData log = new LoggedData(p, b, LogType.BLOCK_BROKEN_PLAYER);
        plugin.getLoggedDataMap().get(LogType.BLOCK_BROKEN_PLAYER).push(log);
        plugin.getServer().broadcastMessage("pushed new block player break log onto stack.");
    }

    @EventHandler
    public void onExplosionBreak(EntityExplodeEvent e){
        if (e.blockList().isEmpty()) return;
        LoggedData log = new LoggedData(e.getEntity());
        plugin.getLoggedDataMap().get(LogType.BLOCK_BROKEN_EXPLOSION).push(log);
        plugin.getServer().broadcastMessage("pushed new block break explosion onto stack.");
    }


}
