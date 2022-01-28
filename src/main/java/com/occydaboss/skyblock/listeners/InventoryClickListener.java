package com.occydaboss.skyblock.listeners;

import com.occydaboss.skyblock.guis.ShopMainGUI;
import com.occydaboss.skyblock.util.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        try {
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem().equals(filler)) {
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().equals(exit) || e.getCurrentItem().equals(cancel)) {
                player.closeInventory();
                if (Level.getMainLevel(player) == 0) {
                    player.openInventory(ShopMainGUI.getLevel0(player));
                } else if (Level.getMainLevel(player) == 1) {
                    player.openInventory(ShopMainGUI.getLevel1(player));
                }
                e.setCancelled(true);
                return;
            }
        } catch (NullPointerException npe) {
            return;
        }
    }
}
