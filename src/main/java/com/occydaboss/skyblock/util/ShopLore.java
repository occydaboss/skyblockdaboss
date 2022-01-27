package com.occydaboss.skyblock.util;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShopLore {
    public static ArrayList<String> getLore(float price) {
        DecimalFormat df = new DecimalFormat("#####.##");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Buy Price: $" + df.format(price) + " / Sell Price: $" + df.format(price / 2));
        lore.add(ChatColor.YELLOW + "LMB to buy, SHIFT + LMB to sell inventory, RMB to sell");
        return lore;
    }

    public static ArrayList<String> noSellLore(float price) {
        DecimalFormat df = new DecimalFormat("#####.##");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Buy Price: $" + df.format(price) + " / You cannot sell this item");
        lore.add(ChatColor.YELLOW + "LMB to buy");
        return lore;
    }
}
