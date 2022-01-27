package com.occydaboss.skyblock.util;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.occydaboss.skyblock.SkyBlock;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
            SkyBlock.logger.info("Found island at " + x + ", " + z);
            return new Location(Bukkit.getWorld("islands"), x, 62, z);
        }
        return null;
    }
}
