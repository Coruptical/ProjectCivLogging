package com.coruptical.projectcivlogging.event;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import com.coruptical.projectcivlogging.models.LogType;
import com.coruptical.projectcivlogging.models.LoggedData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();

        LoggedData log = new LoggedData(p, b, LogType.BLOCK_PLACED);
        plugin.getLoggedDataMap().get(LogType.BLOCK_PLACED).push(log);
        plugin.getServer().broadcastMessage("pushed new block place log onto stack.");
    }


}
