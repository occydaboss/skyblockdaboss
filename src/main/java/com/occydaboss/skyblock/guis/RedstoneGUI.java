package com.occydaboss.skyblock.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class RedstoneGUI {
    public static Inventory getLevel1(Player player) {
        Inventory i = Bukkit.createInventory(player, 18, ChatColor.GOLD.toString() + ChatColor.BOLD + "Redstone");
        ItemStack[] items = {
                redstone, redstoneTorch, repeater, piston, hopper, dropper, dispenser, noteBlock, tnt,
                filler, filler, filler, filler, filler, filler, filler, filler, exit
        };
        i.setContents(items);
        return i;
    }
}
