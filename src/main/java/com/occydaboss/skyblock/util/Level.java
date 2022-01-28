package com.occydaboss.skyblock.util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.occydaboss.skyblock.SkyBlock;
import org.bson.Document;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;

public class Level {
    public static void addPlayerToDatabase(Player player) {
        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (!collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            Document document = new Document("_id", player.getUniqueId().toString())
                    .append("level", 0)
                    .append("mining", 0.0d)
                    .append("farming", 0.0d)
                    .append("combat", 0.0d);
            collection.insertOne(document);
        } else {
            SkyBlock.logger.info("Player with UUID " + player.getUniqueId() + " already exists in database");
        }
    }

    public static void resetPlayerLevels(Player player) {
        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        collection.findOneAndDelete(Filters.eq("_id", player.getUniqueId().toString()));
        addPlayerToDatabase(player);
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

    public static boolean setMiningLevel(Player player, double level) {
        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            collection.updateOne(collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next(),
                    Updates.set("mining", level));

            return true;
        } else {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
            return false;
        }
    }

    public static double getMiningLevel(Player player) {
        double level = -1;

        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (!collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
        } else {
            if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
                Document document = collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next();
                level = (double) document.get("mining");
            }
        }
        return level;
    }

    public static boolean setFarmingLevel(Player player, double level) {
        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            collection.updateOne(collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next(),
                    Updates.set("farming", level));

            return true;
        } else {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
            return false;
        }
    }

    public static double getFarmingLevel(Player player) {
        double level = -1;

        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (!collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
        } else {
            if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
                Document document = collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next();
                level = (double) document.get("farming");
            }
        }
        return level;
    }

    public static boolean setCombatLevel(Player player, double level) {
        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            collection.updateOne(collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next(),
                    Updates.set("combat", level));

            return true;
        } else {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
            return false;
        }
    }

    public static double getCombatLevel(Player player) {
        double level = -1;

        MongoCollection<Document> collection = SkyBlock.database.getCollection("players");
        if (!collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
            SkyBlock.logger.info("No record of player with UUID " + player.getUniqueId());
        } else {
            if (collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().hasNext()) {
                Document document = collection.find(Filters.eq("_id", player.getUniqueId().toString())).iterator().next();
                level = (double) document.get("combat");
            }
        }
        return level;
    }
}
