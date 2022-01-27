package com.occydaboss.skyblock.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class BlocksGUI {
    public static Inventory getLevel0(Player player) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Blocks");
        ItemStack[] items = {
                stone, cobblestone, andesite, diorite, granite, grass, dirt, oakPlanks, sprucePlanks,
                birchPlanks, sand, gravel, oakLog, spruceLog, birchLog, sandstone, whiteWool, ice,
                filler, filler, filler, filler, filler, filler, filler, filler, exit
        };
        i.setContents(items);
        return i;
    }

    public static Inventory getLevel1(Player player) {
        Inventory i = Bukkit.createInventory(player, 36, ChatColor.GOLD.toString() + ChatColor.BOLD + "Blocks");
        ItemStack[] items = {
                stone, cobblestone, andesite, diorite, granite, grass, dirt, oakPlanks, sprucePlanks,
                birchPlanks, sand, gravel, oakLog, spruceLog, birchLog, sandstone, whiteWool, ice,
                filler, oakLeaves, spruceLeaves, birchLeaves, filler, redSand, stoneBrick, redSandstone, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, exit
        };
        i.setContents(items);
        return i;
    }
}
