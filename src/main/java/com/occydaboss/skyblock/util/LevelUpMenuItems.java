package com.occydaboss.skyblock.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class LevelUpMenuItems {
    public static ItemStack levelUpButton(Player player) {
        ItemStack noXP = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED + "You don't have enough XP!").build();
        ItemStack levelUpButton = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(ChatColor.GREEN + "Level Up!").build();
        if (Level.getMainLevel(player) == 0) {
            if (Level.getMiningLevel(player) >= 100 && Level.getFarmingLevel(player) >= 100) {
                return levelUpButton;
            } else {
                return noXP;
            }
        } else if (Level.getMainLevel(player) == 1) {
            if (Level.getMiningLevel(player) >= 1000 && Level.getFarmingLevel(player) >= 1000) {
                return levelUpButton;
            } else {
                return noXP;
            }
        }
        return noXP;
    }

    public static ItemStack miningXP(Player player) {
        ItemStack item = new ItemStack(Material.STONE);
        if (Level.getMainLevel(player) == 0) {
            if (Level.getMiningLevel(player) >= 100) {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.GREEN + "Completed!").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            } else {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.YELLOW + "Mining XP: " + Level.getMiningLevel(player) + " / 100").setLore(ChatColor.WHITE + "Get Mining XP by breaking Cobblestone").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            }
        } else if (Level.getMainLevel(player) == 1) {
            if (Level.getMiningLevel(player) >= 1000) {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.GREEN + "Completed!").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            } else {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.YELLOW + "Mining XP: " + Level.getMiningLevel(player) + " / 1000").setLore(ChatColor.WHITE + "Get Mining XP by breaking Cobblestone and Sandstone").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            }
        }
        return item;
    }

    public static ItemStack farmingXP(Player player) {
        ItemStack item = new ItemStack(Material.STONE);
        if (Level.getMainLevel(player) == 0) {
            if (Level.getFarmingLevel(player) >= 100) {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.GREEN + "Completed!").build();
            } else {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.YELLOW + "Farming XP: " + Level.getFarmingLevel(player) + " / 100").setLore(ChatColor.WHITE + "Get Farming XP by harvesting Sugar Cane").build();
            }
        } else if (Level.getMainLevel(player) == 1) {
            if (Level.getFarmingLevel(player) >= 1000) {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.GREEN + "Completed!").build();
            } else {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.YELLOW + "Farming XP: " + Level.getFarmingLevel(player) + " / 1000").setLore(ChatColor.WHITE + "Get Farming XP by harvesting Melons and Pumpkins").build();
            }
        }
        return item;
    }

    public static ItemStack combatXP(Player player) {
        ItemStack item = new ItemStack(Material.STONE);
        if (Level.getMainLevel(player) == 1) {
            if (Level.getCombatLevel(player) >= 1) {
                item = new ItemBuilder(Material.GOLDEN_SWORD).setDisplayName(ChatColor.GREEN + "Completed!").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            } else {
                item = new ItemBuilder(Material.GOLDEN_SWORD).setDisplayName(ChatColor.YELLOW + "You must defeat Bob to progress").setLore(ChatColor.WHITE + "Summon Bob with an Enchanted Flesh").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            }
        }
        return item;
    }
}
