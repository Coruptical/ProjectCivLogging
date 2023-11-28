package com.coruptical.projectcivlogging.event;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryEditListener implements Listener {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        InventoryType type = e.getInventory().getType();

        if (e.isCancelled()) return;
        if  (type.equals(InventoryType.CHEST) || type.equals(InventoryType.BARREL) || type.equals(InventoryType.FURNACE)){
            Inventory inventory = e.getInventory();
            plugin.getInventoryContentsMap().put((Player) e.getPlayer(), inventory.getContents());
        }
    }

    @EventHandler
    public void onInventoryClose(){
        return;
    }
}
