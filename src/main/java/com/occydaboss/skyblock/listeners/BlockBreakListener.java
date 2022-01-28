package com.occydaboss.skyblock.listeners;

import com.occydaboss.skyblock.util.Level;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        int level = Level.getMainLevel(player);
        Block block = e.getBlock();

        if (level == 0) {
            if (block.getType().equals(Material.COBBLESTONE)) {
                if (Level.getMiningLevel(player) < 100) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "+5 mining xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setMiningLevel(player, Level.getMiningLevel(player) + 5);
                }
            } else if (block.getType().equals(Material.SUGAR_CANE)) {
                if (Level.getFarmingLevel(player) < 100) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "+5 farming xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setFarmingLevel(player, Level.getFarmingLevel(player) + 5);
                }
            }
        } else if (level == 1) {
            if (block.getType().equals(Material.COBBLESTONE)) {
                if (Level.getMiningLevel(player) < 1000) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "+5 mining xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setMiningLevel(player, Level.getMiningLevel(player) + 5);
                }
            } else if (block.getType().equals(Material.SANDSTONE)) {
                if (Level.getMiningLevel(player) < 1000) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "+7.5 mining xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setMiningLevel(player, Level.getMiningLevel(player) + 7.5);
                }
            } else if (block.getType().equals(Material.MELON) || block.getType().equals(Material.PUMPKIN)) {
                if (Level.getMiningLevel(player) < 1000) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "+5 farming xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setFarmingLevel(player, Level.getFarmingLevel(player) + 5);
                }
            }
        }
    }
}
