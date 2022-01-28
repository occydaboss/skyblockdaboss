package com.occydaboss.skyblock.listeners.menulisteners;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.Level;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LevelUpMenu implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Level Up")) {
            if (e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                switch (Level.getMainLevel(player)) {
                    case 0:
                        if (SkyBlock.economy.has(player, 1000)) {
                            Level.resetSubLevels(player);
                            Level.setMainLevel(player, Level.getMainLevel(player) + 1);
                            player.sendMessage(AddPrefix.addPrefix("You are now level " + Level.getMainLevel(player)));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                            player.closeInventory();
                            SkyBlock.economy.withdrawPlayer(player, 1000);
                        }
                        break;
                    case 1:
                        if (SkyBlock.economy.has(player, 10000)) {
                            Level.resetSubLevels(player);
                            Level.setMainLevel(player, Level.getMainLevel(player) + 1);
                            player.sendMessage(AddPrefix.addPrefix("You are now level " + Level.getMainLevel(player)));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                            player.closeInventory();
                            SkyBlock.economy.withdrawPlayer(player, 10000);
                        }
                        break;
                }
            }

            e.setCancelled(true);
        }
    }
}
