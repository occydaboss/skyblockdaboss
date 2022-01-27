package com.occydaboss.skyblock.guis;

import com.occydaboss.skyblock.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class BuySellGUI {
    public static Inventory getBuyMenu(Player player, ItemStack item, float price) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Buy Menu");
        ItemStack display = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "$" + price).build();
        ItemStack[] items = {
                rem1, filler, filler, filler, filler, filler, filler, filler, add1,
                rem10, filler, filler, filler, item, filler, filler, filler, add10,
                set1, filler, cancel, filler, display, filler, confirm, filler, set64
        };
        i.setContents(items);
        return i;
    }

    public static Inventory getSellMenu(Player player, ItemStack item, float price) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Sell Menu");
        ItemStack display = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(ChatColor.GOLD + "$" + Math.round((price) * 100.0) / 100.0).build();
        ItemStack[] items = {
                rem1, filler, filler, filler, filler, filler, filler, filler, add1,
                rem10, filler, filler, filler, item, filler, filler, filler, add10,
                set1, filler, cancel, filler, display, filler, confirm, filler, set64
        };
        i.setContents(items);
        return i;
    }
}
