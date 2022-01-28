package com.occydaboss.skyblock.listeners.menulisteners;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.guis.BuySellGUI;
import com.occydaboss.skyblock.guis.MiscGUI;
import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.InventoryTools;
import com.occydaboss.skyblock.util.Price;
import com.occydaboss.skyblock.util.TextCase;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ShopMenu implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (
                ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Blocks") ||
                        ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Materials") ||
                        ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Farming") ||
                        ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Redstone") ||
                        ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Miscellaneous") ||
                        ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Mob Drops")
        ) {
            if (e.getClick().isShiftClick()) {
                ItemStack cleanItem = new ItemStack(e.getCurrentItem().getType());
                float price = Price.getSellFromShopItem(e.getCurrentItem());
                int amount = InventoryTools.getAmount(player, cleanItem);

                if (amount <= 0) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient items."));
                } else {
                    player.getInventory().removeItem(new ItemStack(cleanItem.getType(), amount));
                    SkyBlock.economy.depositPlayer(player, Math.round((amount * price) * 100.0) / 100.0);

                    player.sendMessage(AddPrefix.addPrefix(ChatColor.GREEN + "Successfully sold " + amount + "x " + TextCase.setCase(String.valueOf(cleanItem.getType()).replaceAll("_", " ")) + " giving you $" + amount * price));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                }

                e.setCancelled(true);
            } else if (e.getClick().isLeftClick()) {
                float price = Price.getPriceFromShopItem(e.getCurrentItem());
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setLore(Collections.singletonList(meta.getLore().get(0)));
                ItemStack item = e.getCurrentItem();
                item.setItemMeta(meta);

                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, price));
                e.setCancelled(true);
            } else if (e.getClick().isRightClick()) {
                ItemStack item = e.getCurrentItem();
                if (e.getClickedInventory() == MiscGUI.getLevel1(player)) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You cannot sell this item."));
                    e.setCancelled(true);
                }
                float price = Price.getSellFromShopItem(e.getCurrentItem());
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                meta.setLore(Collections.singletonList(meta.getLore().get(0)));
                item.setItemMeta(meta);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, price));
                e.setCancelled(true);
            }
        }
    }
}
