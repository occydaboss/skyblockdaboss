package com.occydaboss.skyblock.listeners.menulisteners;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.guis.BuySellGUI;
import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.InventoryTools;
import com.occydaboss.skyblock.util.Price;
import com.occydaboss.skyblock.util.TextCase;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class SellMenu implements Listener {
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Sell Menu")) {
            ItemStack item = e.getView().getItem(13);
            float price = Float.parseFloat(e.getView().getItem(22).getItemMeta().getDisplayName().split("\\$")[1]);

            if (e.getCurrentItem().equals(rem1)) {
                if (item.getAmount() - 1 < 1) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() - 1);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, price - Price.getSellFromShopItem(item)));
            } else if (e.getCurrentItem().equals(rem10)) {
                if (item.getAmount() - 10 < 1) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() - 10);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, price - (10 * Price.getSellFromShopItem(item))));
            } else if (e.getCurrentItem().equals(add1)) {
                if (item.getAmount() + 1 > 64) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() + 1);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, price + Price.getSellFromShopItem(item)));
            } else if (e.getCurrentItem().equals(add10)) {
                if (item.getAmount() + 10 > 64) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() + 10);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, price + (10 * Price.getSellFromShopItem(item))));
            } else if (e.getCurrentItem().equals(set1)) {
                item.setAmount(1);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, Price.getSellFromShopItem(item)));
            } else if (e.getCurrentItem().equals(set64)) {
                item.setAmount(64);
                player.closeInventory();
                player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
            } else if (e.getCurrentItem().equals(confirm)) {
                int amount = item.getAmount();
                ItemStack cleanItem = new ItemStack(item.getType(), amount);

                if (InventoryTools.getAmount(player, cleanItem) >= amount) {
                    player.getInventory().removeItem(cleanItem);
                    SkyBlock.economy.depositPlayer(player, Math.round((price) * 100.0) / 100.0);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.GREEN + "Successfully sold " + amount + "x " + TextCase.setCase(String.valueOf(item.getType()).replaceAll("_", " ")) + " giving you $" + price));
                } else {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient items."));
                }
            }
            e.setCancelled(true);
        }
    }
}
