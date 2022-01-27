package com.occydaboss.skyblock.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class LevelUpGUI {
    private static ItemStack[] items;

    public static Inventory getLevel0(Player player) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Level Up");
        items = new ItemStack[]
                {
                        filler, filler, filler, filler, filler, filler, filler, filler, filler,
                        filler, filler, miningXP(player), filler, levelUpButton(player), filler, farmingXP(player), filler, filler,
                        filler, filler, filler, filler, filler, filler, filler, filler, filler
                };

        i.setContents(items);
        return i;
    }

    public static Inventory getLevel1(Player player) {
        Inventory i = Bukkit.createInventory(player, 27, ChatColor.GOLD.toString() + ChatColor.BOLD + "Level Up");
        items = new ItemStack[]
                {
                        filler, filler, filler, filler, filler, filler, filler, filler, filler,
                        filler, filler, miningXP(player), filler, levelUpButton(player), filler, farmingXP(player), filler, filler,
                        filler, filler, filler, filler, combatXP(player), filler, filler, filler, filler
                };

        i.setContents(items);
        return i;
    }

}
