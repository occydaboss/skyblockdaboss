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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class BuyMenu implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Buy Menu")) {
            ItemStack item = e.getView().getItem(13);
            float price = Float.parseFloat(e.getView().getItem(22).getItemMeta().getDisplayName().split("\\$")[1]);

            // apparently a switch doesnt work here jdfijdfjk i hate life
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem.equals(rem1)) {
                if (item.getAmount() - 1 < 1) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() - 1);
                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, price - Price.getPriceFromShopItem(item)));
            } else if (currentItem.equals(rem10)) {
                if (item.getAmount() - 10 < 1) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() - 10);
                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, price - (10 * Price.getPriceFromShopItem(item))));
            } else if (currentItem.equals(add1)) {
                if (item.getAmount() + 1 > 64) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() + 1);
                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, price + Price.getPriceFromShopItem(item)));
            } else if (currentItem.equals(add10)) {
                if (item.getAmount() + 10 > 64) {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                    e.setCancelled(true);
                }

                item.setAmount(item.getAmount() + 10);
                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, price + (10 * Price.getPriceFromShopItem(item))));
            } else if (currentItem.equals(set1)) {
                item.setAmount(1);
                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, Price.getPriceFromShopItem(item)));
            } else if (currentItem.equals(set64)) {
                item.setAmount(64);
                player.closeInventory();
                player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
            } else if (currentItem.equals(currentItem)) {
                int amount = item.getAmount();
                ItemStack cleanItem = new ItemStack(item.getType(), amount);

                if (!(SkyBlock.economy.getBalance(player) - price <= 0)) {
                    if (InventoryTools.hasAvaliableSlot(player)) {
                        player.getInventory().addItem(cleanItem);
                        SkyBlock.economy.withdrawPlayer(player, price);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.GREEN + "Successfully bought " + amount + "x " + TextCase.setCase(String.valueOf(item.getType()).replaceAll("_", " ")) + " costing you $" + price));
                    } else {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient inventory space."));
                    }
                } else {
                    player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient funds."));
                }
            }

            e.setCancelled(true);
        }
    }
}
