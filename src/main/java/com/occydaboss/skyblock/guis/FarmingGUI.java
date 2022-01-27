package com.occydaboss.skyblock.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class FarmingGUI {
    public static Inventory getLevel0(Player player) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Farming");
        ItemStack[] items = {
                filler, wheat, sugarCane, carrot, potato, cactus, melonSlice, pumpkin, filler,
                filler, wheatSeeds, melonSeeds, pumpkinSeeds, cocoaBeans, oakSapling, spruceSapling, birchSapling, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, exit
        };
        i.setContents(items);
        return i;
    }
}
