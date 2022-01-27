package com.occydaboss.skyblock.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class ShopMainGUI {
    public static Inventory getLevel0(Player player) {
        Inventory i = Bukkit.createInventory(player, 9, ChatColor.GOLD.toString() + ChatColor.BOLD + "Main Menu");
        ItemStack[] items = {
                filler, filler, blocksButton, filler, materialsButton, filler, farmingButton, filler, filler
        };
        i.setContents(items);
        return i;
    }

    public static Inventory getLevel1(Player player) {
        Inventory i = Bukkit.createInventory(player, 18, ChatColor.GOLD.toString() + ChatColor.BOLD + "Main Menu");
        ItemStack[] items = {
                filler, filler, blocksButton, filler, materialsButton, filler, farmingButton, filler, filler,
                filler, filler, redstoneButton, filler, miscButton, filler, mobDropsButton, filler, filler
        };
        i.setContents(items);
        return i;
    }
}
