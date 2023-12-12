package com.coruptical.projectcivlogging.event;

import com.coruptical.projectcivlogging.ProjectCivLogging;
import com.coruptical.projectcivlogging.models.LogType;
import com.coruptical.projectcivlogging.models.LoggedData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;

public class InventoryEditListener implements Listener {
    private final ProjectCivLogging plugin = ProjectCivLogging.getInstance();

    private final HashSet<InventoryType> types;

    public InventoryEditListener() {
        this.types = new HashSet<>();
        types.add(InventoryType.CHEST);
        types.add(InventoryType.BARREL);
        types.add(InventoryType.FURNACE);
        types.add(InventoryType.BLAST_FURNACE);
        types.add(InventoryType.SMOKER);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        InventoryType type = e.getInventory().getType();

        if (e.isCancelled()) return;
        if  (types.contains(type)){
            Inventory inventory = e.getInventory();
            plugin.getInventoryContentsMap().put((Player) e.getPlayer(), inventory.getContents());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        InventoryType type = e.getInventory().getType();

        if (types.contains(type)){
            Player p = (Player) e.getPlayer();
            Inventory inventory = e.getInventory();
            ItemStack[] contents = inventory.getContents();
            ItemStack[] oldContents = plugin.getInventoryContentsMap().get(p);

            if (!Arrays.equals(contents, oldContents)){
                Location inventoryLocation = inventory.getLocation();
                LoggedData log = new LoggedData(p, inventoryLocation.getBlock());
                plugin.getLoggedDataMap().get(LogType.PLAYER_EDITED_INVENTORY).push(log);
            }
            plugin.getInventoryContentsMap().remove(p);
        }
    }
}
