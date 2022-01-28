package com.occydaboss.skyblock.listeners;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.util.IslandAPI;
import com.occydaboss.skyblock.util.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Level.addPlayerToDatabase(e.getPlayer());

        SkyBlock.scoreboard.setTitle(e.getPlayer(), ChatColor.LIGHT_PURPLE + "SkyBlock");
        SkyBlock.updateScoreboard(e.getPlayer());
        SkyBlock.scoreboard.addPlayer(e.getPlayer());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.getInstance(), new Runnable() {
            @Override
            public void run() {
                SkyBlock.updateScoreboard(e.getPlayer());
            }
        }, 10, 10);

        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyBlock.getInstance(), new Runnable() {
            @Override
            public void run() {
                SkyBlock.worldBorderApi.setBorder(e.getPlayer(), 50, IslandAPI.getIslandCoordinates(e.getPlayer()));
            }
        }, 5);
    }
}
