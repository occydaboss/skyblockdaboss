package com.occydaboss.skyblock.commands;

import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.EmptyChunkGenerator;
import com.occydaboss.skyblock.util.IslandAPI;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class CommandResetIslands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player[] players = Bukkit.getWorld("islands").getPlayers().toArray(new Player[0]);
        for (Player player : players) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 255, 0));
        }

        sender.sendMessage(AddPrefix.addPrefix("Deleting Islands from Database..."));
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        for (OfflinePlayer player : offlinePlayers) {
            Player onlinePlayer = Bukkit.getPlayer(player.getUniqueId());
            IslandAPI.removePlayer(onlinePlayer);
        }
        for (Player player : onlinePlayers) {
            IslandAPI.removePlayer(player);
        }
        sender.sendMessage(AddPrefix.addPrefix("Deleting World..."));
        Bukkit.unloadWorld("islands", false);
        try {
            FileUtils.deleteDirectory(new File("islands"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender.sendMessage(AddPrefix.addPrefix("Creating World..."));
        WorldCreator wc = new WorldCreator("islands");
        wc.generator(new EmptyChunkGenerator());
        wc.createWorld();
        sender.sendMessage(AddPrefix.addPrefix("Done!"));
        return true;
    }
}
