package com.occydaboss.skyblock.util;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.occydaboss.skyblock.SkyBlock;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.Iterator;

public class IslandAPI {
    public static Location getIslandCoordinates(Player player) {
        FindIterable<Document> iterable = SkyBlock.database.getCollection("islands")
                .find(Filters.eq("_id", player.getUniqueId().toString()));
        Iterator<Document> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            player.sendMessage(AddPrefix.addPrefix("Player " + player + " does not have an island!"));
            return null;
        }
        while (iterator.hasNext()) {
            Document document = iterator.next();
            int x = (int) document.get("x");
            int z = (int) document.get("z");
            return new Location(Bukkit.getWorld("islands"), x, 62, z);
        }
        return null;
    }

    public static Location[] getIslandBounds(Player player) {
        FindIterable<Document> iterable = SkyBlock.database.getCollection("islands")
                .find(Filters.eq("_id", player.getUniqueId().toString()));
        Iterator<Document> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            player.sendMessage(AddPrefix.addPrefix("Player " + player + " does not have an island!"));
            return null;
        }
        while (iterator.hasNext()) {
            Document document = iterator.next();
            int x1 = (int) document.get("x") - 50;
            int x2 = (int) document.get("x") + 50;
            int z1 = (int) document.get("z") - 50;
            int z2 = (int) document.get("z") + 50;
            return new Location[]{new Location(Bukkit.getWorld("islands"), x1, 0, z1), new Location(Bukkit.getWorld("islands"), x2, 256, z2)};
        }
        return null;
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
