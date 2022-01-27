package com.occydaboss.skyblock.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class MaterialsGUI {
    public static Inventory getLevel0(Player player) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Materials");
        ItemStack[] items = {
                filler, cobblestone, sandstone, coal, lapis, redstone, ironOre, goldOre, filler,
                filler, filler, emerald, ironIngot, filler, goldIngot, diamond, filler, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, exit
        };
        i.setContents(items);
        return i;
    }

    public static Inventory getLevel2(Player player) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Materials");
        ItemStack[] items = {
                filler, cobblestone, sandstone, coal, lapis, redstone, ironOre, goldOre, filler,
                filler, filler, emerald, ironIngot, redSandstone, goldIngot, diamond, filler, filler,
                filler, filler, filler, filler, filler, filler, filler, filler, exit
        };
        i.setContents(items);
        return i;
    }

}
