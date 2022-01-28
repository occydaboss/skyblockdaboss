package com.occydaboss.skyblock.listeners.menulisteners;

import com.occydaboss.skyblock.guis.*;
import com.occydaboss.skyblock.util.Level;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class MainMenu implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Main Menu")) {
            // switch is incompatible here i hate this
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().equals(blocksButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) == 0) {
                        player.openInventory(BlocksGUI.getLevel0(player));
                    } else if (Level.getMainLevel(player) == 1) {
                        player.openInventory(BlocksGUI.getLevel1(player));
                    }
                } else if (e.getCurrentItem().equals(materialsButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) <= 1) {
                        player.openInventory(MaterialsGUI.getLevel0(player));
                    }
                } else if (e.getCurrentItem().equals(farmingButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) <= 1) {
                        player.openInventory(FarmingGUI.getLevel0(player));
                    }
                } else if (e.getCurrentItem().equals(redstoneButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) >= 1) {
                        player.openInventory(RedstoneGUI.getLevel1(player));
                    }
                } else if (e.getCurrentItem().equals(miscButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) >= 1) {
                        player.openInventory(MiscGUI.getLevel1(player));
                    }
                } else if (e.getCurrentItem().equals(mobDropsButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) >= 1) {
                        player.openInventory(MobDropsGUI.getLevel1(player));
                    }
                }
            }
            e.setCancelled(true);
        }
    }
}
