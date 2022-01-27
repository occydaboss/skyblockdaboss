package com.occydaboss.skyblock.commands;

import com.occydaboss.skyblock.util.Level;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetLevel implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (Bukkit.getPlayer(args[0]) == null || !NumberUtils.isNumber(args[1]) || args[0] == null || args[1] == null)
            return false;
        Player player = Bukkit.getPlayer(args[0]);
        Level.setMainLevelWithMessage(player, Integer.parseInt(args[1]));
        return true;
    }
}
