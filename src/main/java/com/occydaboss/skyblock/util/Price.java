package com.occydaboss.skyblock.util;

import org.bukkit.inventory.ItemStack;

public class Price {
    public static float getPriceFromShopItem(ItemStack item) {
        String line = item.getItemMeta().getLore().get(0).split("/")[0];

        return Float.parseFloat(line.split("\\$")[1]);
    }

    public static float getSellFromShopItem(ItemStack item) {
        String line = item.getItemMeta().getLore().get(0).split("/")[1];

        return Float.parseFloat(line.split("\\$")[1]);
    }
}
