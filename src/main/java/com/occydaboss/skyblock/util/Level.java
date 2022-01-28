package com.occydaboss.skyblock.util;

import com.occydaboss.skyblock.SkyBlock;
import org.bukkit.Instrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Level {

    public static NamespacedKey mainLevel = new NamespacedKey(SkyBlock.getInstance(), "level");
    public static NamespacedKey mining = new NamespacedKey(SkyBlock.getInstance(), "mining");
    public static NamespacedKey farming = new NamespacedKey(SkyBlock.getInstance(), "farming");
    public static NamespacedKey combat = new NamespacedKey(SkyBlock.getInstance(), "combat");

    public static void addPlayerToDatabase(Player player) {

        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (!playerData.has(mainLevel)) {
            playerData.set(mainLevel, PersistentDataType.INTEGER, 0);
        }

        if (!playerData.has(mining)) {
            playerData.set(mining, PersistentDataType.DOUBLE, 0d);
        }

        if (!playerData.has(farming)) {
            playerData.set(farming, PersistentDataType.DOUBLE, 0d);
        }

        if (!playerData.has(combat)) {
            playerData.set(combat, PersistentDataType.DOUBLE, 0d);
        }
    }

    public static void resetPlayerLevels(Player player) {

        PersistentDataContainer playerData = player.getPersistentDataContainer();

        playerData.set(mainLevel, PersistentDataType.INTEGER, 0);
        playerData.set(mining, PersistentDataType.DOUBLE, 0d);
        playerData.set(farming, PersistentDataType.DOUBLE, 0d);
        playerData.set(combat, PersistentDataType.DOUBLE, 0d);
    }

    public static void resetSubLevels(Player player) {

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        
        playerData.set(mining, PersistentDataType.DOUBLE, 0d);
        playerData.set(farming, PersistentDataType.DOUBLE, 0d);
        playerData.set(combat, PersistentDataType.DOUBLE, 0d);
    }

    public static void setMainLevel(Player player, int level) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(mainLevel, PersistentDataType.INTEGER, level);
    }

    public static void setMainLevelWithMessage(Player player, int level) {
        setMainLevel(player, level);
        player.sendMessage(AddPrefix.addPrefix("Levelled up to level " + level + "!"));
        player.playNote(player.getLocation(), Instrument.PLING, Note.natural(1, Note.Tone.C));
    }

    public static int getMainLevel(Player player) {
        return player.getPersistentDataContainer().get(mainLevel, PersistentDataType.INTEGER);
    }

    public static void setMiningLevel(Player player, double level) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(mining, PersistentDataType.DOUBLE, level);
    }

    public static double getMiningLevel(@NotNull Player player) {
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
