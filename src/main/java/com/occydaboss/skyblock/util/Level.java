package com.occydaboss.skyblock.util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.occydaboss.skyblock.SkyBlock;
import org.bson.Document;
import org.bukkit.Instrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Level {

    public static NamespacedKey level = new NamespacedKey(SkyBlock.getInstance(), "level");
    public static NamespacedKey mining = new NamespacedKey(SkyBlock.getInstance(), "mining");
    public static NamespacedKey farming = new NamespacedKey(SkyBlock.getInstance(), "farming");
    public static NamespacedKey combat = new NamespacedKey(SkyBlock.getInstance(), "combat");

    public static void addPlayerToDatabase(Player player) {

        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (!playerData.has(level)) {
            playerData.set(level, PersistentDataType.INTEGER, 0);
        }

        if (!playerData.has(mining)) {
            playerData.set(mining, PersistentDataType.DOUBLE, 0d);
        }

        if (!playerData.has(farming)) {
            playerData.set(farming, PersistentDataType.INTEGER, 0);
        }

        if (!playerData.has(combat)) {
            playerData.set(combat, PersistentDataType.INTEGER, 0);
        }
    }

    public static void resetPlayerLevels(Player player) {

        PersistentDataContainer playerData = player.getPersistentDataContainer();

        playerData.set(level, PersistentDataType.INTEGER, 0);
        playerData.set(mining, PersistentDataType.DOUBLE, 0d);
        playerData.set(farming, PersistentDataType.DOUBLE, 0d);
        playerData.set(combat, PersistentDataType.DOUBLE, 0d);
    }

    public static boolean setMainLevel(Player player, int level) {
        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            collection.updateOne(collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next(),
                    Updates.set("level", level));

            return true;
        } else {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
            return false;
        }
    }

    public static void setMainLevelWithMessage(Player player, int level) {
        if (!setMainLevel(player, level)) {
            player.sendMessage(AddPrefix.addPrefix("An error occurred! Please check console."));
        } else {
            player.sendMessage(AddPrefix.addPrefix("Levelled up to level " + level + "!"));
            player.playNote(player.getLocation(), Instrument.PLING, Note.natural(1, Note.Tone.C));

        }
    }

    public static int getMainLevel(Player player) {
        int level = -1;

        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (!collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
        } else {
            if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
                Document document = collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next();
                level = (int) document.get("level");
            }
        }
        return level;
    }

    public static void setMiningLevel(Player player, double level) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(mining, PersistentDataType.DOUBLE, level);
    }

    public static double getMiningLevel(@NotNull Player player) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();

        return player.getPersistentDataContainer().get(mining, PersistentDataType.DOUBLE);
    }

    public static void setFarmingLevel(Player player, double level) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(farming, PersistentDataType.DOUBLE, level);
    }

    public static double getFarmingLevel(Player player) {
        return player.getPersistentDataContainer().get(farming, PersistentDataType.DOUBLE);
    }

    public static void setCombatLevel(Player player, double level) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(combat, PersistentDataType.DOUBLE, level);
    }

    public static double getCombatLevel(Player player) {
        return player.getPersistentDataContainer().get(combat, PersistentDataType.DOUBLE);
    }
}
