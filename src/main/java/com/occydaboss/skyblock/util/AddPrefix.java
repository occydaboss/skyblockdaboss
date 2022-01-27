package com.occydaboss.skyblock.util;

import com.occydaboss.skyblock.SkyBlock;
import org.bukkit.ChatColor;

public class AddPrefix {
    public static String addPrefix(String message) {
        return SkyBlock.getInstance().getConfig().getString("chatPrefix") + ChatColor.RESET + " " + message;
    }
}
