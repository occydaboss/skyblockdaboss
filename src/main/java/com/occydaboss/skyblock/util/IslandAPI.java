package com.occydaboss.skyblock.util;

import com.occydaboss.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BoundingBox;

public class IslandAPI {
    public static NamespacedKey xKey = new NamespacedKey(SkyBlock.getInstance(), "island_x");
    public static NamespacedKey zKey = new NamespacedKey(SkyBlock.getInstance(), "island_z");

    public static void addPlayer(Player player, int x, int z) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (!playerData.has(xKey)) {
            playerData.set(xKey, PersistentDataType.INTEGER, x);
        }

        if (!playerData.has(zKey)) {
            playerData.set(zKey, PersistentDataType.INTEGER, z);
        }
    }

    public static void removePlayer(Player player) {
        if (player == null || player.getPersistentDataContainer() == null) return;
        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (playerData.has(xKey)) {
            playerData.remove(xKey);
        }

        if (playerData.has(zKey)) {
            playerData.remove(zKey);
        }
    }

    public static Location getIslandCoordinates(Player player) {
        int x = player.getPersistentDataContainer().get(xKey, PersistentDataType.INTEGER);
        int z = player.getPersistentDataContainer().get(xKey, PersistentDataType.INTEGER);
        return new Location(Bukkit.getWorld("islands"), x, 62, z);
    }

    public static Location[] getIslandBounds(Player player) {
        int x = player.getPersistentDataContainer().get(xKey, PersistentDataType.INTEGER);
        int z = player.getPersistentDataContainer().get(xKey, PersistentDataType.INTEGER);
        int x1 = x - 50;
        int x2 = x + 50;
        int z1 = z - 50;
        int z2 = z + 50;
        return new Location[]{new Location(Bukkit.getWorld("islands"), x1, 0, z1), new Location(Bukkit.getWorld("islands"), x2, 256, z2)};
    }

    public static BoundingBox getIslandBoundingBox(Player player) {
        return BoundingBox.of(getIslandBounds(player)[0], getIslandBounds(player)[1]);
    }

    public static boolean withinIsland(Player player, Location location) {
        return location.getBlockX() > IslandAPI.getIslandCoordinates(player).getX() - 50 &&
                location.getBlockX() < IslandAPI.getIslandCoordinates(player).getX() + 50 &&
                location.getBlockZ() > IslandAPI.getIslandCoordinates(player).getZ() - 50 &&
                location.getBlockZ() < IslandAPI.getIslandCoordinates(player).getZ() + 50;
    }
}
