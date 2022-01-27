package com.occydaboss.skyblock.commands;

import com.occydaboss.skyblock.guis.ShopMainGUI;
import com.occydaboss.skyblock.util.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandShop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        switch (Level.getMainLevel(player)) {
            case 0 -> player.openInventory(ShopMainGUI.getLevel0(player));
            case 1 -> player.openInventory(ShopMainGUI.getLevel1(player));
        }
        return true;
    }
}
