package com.occydaboss.skyblock.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ShopItems {

    public static ItemStack levelUpButton(Player player) {
        ItemStack noXP = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED + "You don't have enough XP!").build();
        ItemStack levelUpButton = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(ChatColor.GREEN + "Level Up!").build();
        if (Level.getMainLevel(player) == 0) {
            if (Level.getMiningLevel(player) >= 100 && Level.getFarmingLevel(player) >= 100) {
                return levelUpButton;
            } else {
                return noXP;
            }
        } else if (Level.getMainLevel(player) == 1) {
            if (Level.getMiningLevel(player) >= 1000 && Level.getFarmingLevel(player) >= 1000) {
                return levelUpButton;
            } else {
                return noXP;
            }
        }
        return noXP;
    }

    public static ItemStack miningXP(Player player) {
        ItemStack item = new ItemStack(Material.STONE);
        if (Level.getMainLevel(player) == 0) {
            if (Level.getMiningLevel(player) >= 100) {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.GREEN + "Completed!").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            } else {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.YELLOW + "Mining XP: " + Level.getMiningLevel(player)).setLore(ChatColor.WHITE + "Get Mining XP by breaking Cobblestone").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            }
        } else if (Level.getMainLevel(player) == 1) {
            if (Level.getMiningLevel(player) >= 1000) {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.GREEN + "Completed!").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            } else {
                item = new ItemBuilder(Material.GOLDEN_PICKAXE).setDisplayName(ChatColor.YELLOW + "Mining XP: " + Level.getMiningLevel(player)).setLore(ChatColor.WHITE + "Get Mining XP by breaking Cobblestone and Sandstone").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            }
        }
        return item;
    }

    public static ItemStack farmingXP(Player player) {
        ItemStack item = new ItemStack(Material.STONE);
        if (Level.getMainLevel(player) == 0) {
            if (Level.getFarmingLevel(player) >= 100) {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.GREEN + "Completed!").build();
            } else {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.YELLOW + "Farming XP: " + Level.getFarmingLevel(player)).setLore(ChatColor.WHITE + "Get Farming XP by breaking Melons and Pumpkins").build();
            }
        } else if (Level.getMainLevel(player) == 1) {
            if (Level.getFarmingLevel(player) >= 1000) {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.GREEN + "Completed!").build();
            } else {
                item = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.YELLOW + "Farming XP: " + Level.getFarmingLevel(player)).setLore(ChatColor.WHITE + "Get Farming XP by breaking Sugar Cane, Melons, and Pumpkins").build();
            }
        }
        return item;
    }

    public static ItemStack combatXP(Player player) {
        ItemStack item = new ItemStack(Material.STONE);
        if (Level.getMainLevel(player) == 1) {
            if (Level.getCombatLevel(player) == 1) {
                item = new ItemBuilder(Material.GOLDEN_SWORD).setDisplayName(ChatColor.GREEN + "Completed!").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            } else {
                item = new ItemBuilder(Material.GOLDEN_SWORD).setDisplayName(ChatColor.YELLOW + "You must defeat Bob to progress").setLore(ChatColor.WHITE + "Summon Bob with an Enchanted Flesh").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build();
            }
        }
        return item;
    }

    public static final ItemStack filler = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
    public static final ItemStack confirm = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(ChatColor.GREEN + "Confirm Purchase").build();
    public static final ItemStack cancel = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED + "Cancel Purchase").build();
    public static final ItemStack exit = new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Exit").build();

    public static final ItemStack add1 = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(ChatColor.GREEN + "Add 1 Item").build();
    public static final ItemStack add10 = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(ChatColor.GREEN + "Add 10 Items").setAmount(10).build();
    public static final ItemStack set64 = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(ChatColor.GREEN + "Set to 64 Items").setAmount(64).build();
    public static final ItemStack rem1 = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED + "Remove 1 Item").build();
    public static final ItemStack rem10 = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED + "Remove 10 Items").setAmount(10).build();
    public static final ItemStack set1 = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED + "Set to 1 Item").build();

    public static final ItemStack blocksButton = new ItemBuilder(Material.COBBLESTONE).setDisplayName(ChatColor.YELLOW + "Blocks").build();
    public static final ItemStack farmingButton = new ItemBuilder(Material.WHEAT).setDisplayName(ChatColor.YELLOW + "Farming").build();
    public static final ItemStack materialsButton = new ItemBuilder(Material.DIAMOND).setDisplayName(ChatColor.YELLOW + "Materials").build();
    public static final ItemStack redstoneButton = new ItemBuilder(Material.REDSTONE).setDisplayName(ChatColor.YELLOW + "Redstone").build();
    public static final ItemStack miscButton = new ItemBuilder(Material.BEACON).setDisplayName(ChatColor.YELLOW + "Miscellaneous").build();
    public static final ItemStack mobDropsButton = new ItemBuilder(Material.BONE).setDisplayName(ChatColor.YELLOW + "Mob Drops").build();

    /*
    Level 0
     */

    // Blocks
    public static final ItemStack stone = new ItemBuilder(Material.STONE).setLore(ShopLore.getLore(2f)).build();
    public static final ItemStack cobblestone = new ItemBuilder(Material.COBBLESTONE).setLore(ShopLore.getLore(1f)).build();
    public static final ItemStack andesite = new ItemBuilder(Material.ANDESITE).setLore(ShopLore.getLore(3f)).build();
    public static final ItemStack diorite = new ItemBuilder(Material.DIORITE).setLore(ShopLore.getLore(3f)).build();
    public static final ItemStack granite = new ItemBuilder(Material.GRANITE).setLore(ShopLore.getLore(3f)).build();
    public static final ItemStack grass = new ItemBuilder(Material.GRASS_BLOCK).setLore(ShopLore.getLore(10f)).build();
    public static final ItemStack dirt = new ItemBuilder(Material.DIRT).setLore(ShopLore.getLore(2f)).build();
    public static final ItemStack oakPlanks = new ItemBuilder(Material.OAK_PLANKS).setLore(ShopLore.getLore(5f)).build();
    public static final ItemStack sprucePlanks = new ItemBuilder(Material.SPRUCE_PLANKS).setLore(ShopLore.getLore(5f)).build();
    public static final ItemStack birchPlanks = new ItemBuilder(Material.BIRCH_PLANKS).setLore(ShopLore.getLore(5f)).build();
    public static final ItemStack sand = new ItemBuilder(Material.SAND).setLore(ShopLore.getLore(6f)).build();
    public static final ItemStack gravel = new ItemBuilder(Material.GRAVEL).setLore(ShopLore.getLore(6f)).build();
    public static final ItemStack oakLog = new ItemBuilder(Material.OAK_LOG).setLore(ShopLore.getLore(7f)).build();
    public static final ItemStack spruceLog = new ItemBuilder(Material.SPRUCE_LOG).setLore(ShopLore.getLore(7f)).build();
    public static final ItemStack birchLog = new ItemBuilder(Material.BIRCH_LOG).setLore(ShopLore.getLore(7f)).build();
    public static final ItemStack sandstone = new ItemBuilder(Material.SANDSTONE).setLore(ShopLore.getLore(18f)).build();
    public static final ItemStack whiteWool = new ItemBuilder(Material.WHITE_WOOL).setLore(ShopLore.getLore(10f)).build();
    public static final ItemStack ice = new ItemBuilder(Material.ICE).setLore(ShopLore.getLore(15f)).build();

    // Materials
    public static final ItemStack coal = new ItemBuilder(Material.COAL).setLore(ShopLore.getLore(20f)).build();
    public static final ItemStack lapis = new ItemBuilder(Material.LAPIS_LAZULI).setLore(ShopLore.getLore(50f)).build();
    public static final ItemStack redstone = new ItemBuilder(Material.REDSTONE).setLore(ShopLore.getLore(75f)).build();
    public static final ItemStack ironOre = new ItemBuilder(Material.RAW_IRON).setLore(ShopLore.getLore(100f)).build();
    public static final ItemStack goldOre = new ItemBuilder(Material.RAW_GOLD).setLore(ShopLore.getLore(150f)).build();
    public static final ItemStack ironIngot = new ItemBuilder(Material.IRON_INGOT).setLore(ShopLore.getLore(125f)).build();
    public static final ItemStack goldIngot = new ItemBuilder(Material.GOLD_INGOT).setLore(ShopLore.getLore(175f)).build();
    public static final ItemStack emerald = new ItemBuilder(Material.EMERALD).setLore(ShopLore.getLore(200f)).build();
    public static final ItemStack diamond = new ItemBuilder(Material.DIAMOND).setLore(ShopLore.getLore(400f)).build();

    // Farming
    public static final ItemStack wheat = new ItemBuilder(Material.WHEAT).setLore(ShopLore.getLore(50f)).build();
    public static final ItemStack sugarCane = new ItemBuilder(Material.SUGAR_CANE).setLore(ShopLore.getLore(75f)).build();
    public static final ItemStack carrot = new ItemBuilder(Material.CARROT).setLore(ShopLore.getLore(50f)).build();
    public static final ItemStack potato = new ItemBuilder(Material.POTATO).setLore(ShopLore.getLore(50f)).build();
    public static final ItemStack cactus = new ItemBuilder(Material.CACTUS).setLore(ShopLore.getLore(150f)).build();
    public static final ItemStack melonSlice = new ItemBuilder(Material.MELON_SLICE).setLore(ShopLore.getLore(75f)).build();
    public static final ItemStack pumpkin = new ItemBuilder(Material.PUMPKIN).setLore(ShopLore.getLore(200f)).build();
    public static final ItemStack wheatSeeds = new ItemBuilder(Material.WHEAT_SEEDS).setLore(ShopLore.getLore(10f)).build();
    public static final ItemStack melonSeeds = new ItemBuilder(Material.MELON_SEEDS).setLore(ShopLore.getLore(15f)).build();
    public static final ItemStack pumpkinSeeds = new ItemBuilder(Material.PUMPKIN_SEEDS).setLore(ShopLore.getLore(15f)).build();
    public static final ItemStack cocoaBeans = new ItemBuilder(Material.COCOA_BEANS).setLore(ShopLore.getLore(30f)).build();
    public static final ItemStack oakSapling = new ItemBuilder(Material.OAK_SAPLING).setLore(ShopLore.getLore(20f)).build();
    public static final ItemStack spruceSapling = new ItemBuilder(Material.SPRUCE_SAPLING).setLore(ShopLore.getLore(20f)).build();
    public static final ItemStack birchSapling = new ItemBuilder(Material.BIRCH_SAPLING).setLore(ShopLore.getLore(20f)).build();

     /*
    Level 1
     */

    // Blocks
    public static final ItemStack oakLeaves = new ItemBuilder(Material.OAK_LEAVES).setLore(ShopLore.getLore(8)).build();
    public static final ItemStack spruceLeaves = new ItemBuilder(Material.SPRUCE_LEAVES).setLore(ShopLore.getLore(8)).build();
    public static final ItemStack birchLeaves = new ItemBuilder(Material.BIRCH_LEAVES).setLore(ShopLore.getLore(8)).build();
    public static final ItemStack redSand = new ItemBuilder(Material.RED_SAND).setLore(ShopLore.getLore(10)).build();
    public static final ItemStack stoneBrick = new ItemBuilder(Material.STONE_BRICKS).setLore(ShopLore.getLore(8)).build();
    public static final ItemStack redSandstone = new ItemBuilder(Material.RED_SANDSTONE).setLore(ShopLore.getLore(5)).build();

    // Redstone
    public static final ItemStack repeater = new ItemBuilder(Material.REPEATER).setLore(ShopLore.getLore(300f)).build();
    public static final ItemStack redstoneTorch = new ItemBuilder(Material.REDSTONE_TORCH).setLore(ShopLore.getLore(80f)).build();
    public static final ItemStack piston = new ItemBuilder(Material.PISTON).setLore(ShopLore.getLore(250f)).build();
    public static final ItemStack hopper = new ItemBuilder(Material.HOPPER).setLore(ShopLore.getLore(700f)).build();
    public static final ItemStack dropper = new ItemBuilder(Material.DROPPER).setLore(ShopLore.getLore(125f)).build();
    public static final ItemStack dispenser = new ItemBuilder(Material.DISPENSER).setLore(ShopLore.getLore(620f)).build();
    public static final ItemStack noteBlock = new ItemBuilder(Material.NOTE_BLOCK).setLore(ShopLore.getLore(125f)).build();
    public static final ItemStack tnt = new ItemBuilder(Material.TNT).setLore(ShopLore.getLore(2000f)).build();

    // Misc
    public static final ItemStack bucket = new ItemBuilder(Material.BUCKET).setLore(ShopLore.noSellLore(375f)).build();
    public static final ItemStack waterBucket = new ItemBuilder(Material.WATER_BUCKET).setLore(ShopLore.noSellLore(400f)).build();
    public static final ItemStack lavaBucket = new ItemBuilder(Material.LAVA_BUCKET).setLore(ShopLore.noSellLore(450f)).build();
    public static final ItemStack milkBucket = new ItemBuilder(Material.MILK_BUCKET).setLore(ShopLore.noSellLore(400f)).build();
    public static final ItemStack lead = new ItemBuilder(Material.LEAD).setLore(ShopLore.noSellLore(620f)).build();
    public static final ItemStack nameTag = new ItemBuilder(Material.NAME_TAG).setLore(ShopLore.noSellLore(620f)).build();
    public static final ItemStack saddle = new ItemBuilder(Material.SADDLE).setLore(ShopLore.noSellLore(620f)).build();
    public static final ItemStack compass = new ItemBuilder(Material.COMPASS).setLore(ShopLore.noSellLore(600f)).build();
    public static final ItemStack clock = new ItemBuilder(Material.CLOCK).setLore(ShopLore.noSellLore(600f)).build();

    // Mob Drops
    public static final ItemStack flesh = new ItemBuilder(Material.ROTTEN_FLESH).setLore(ShopLore.getLore(175f)).build();
    public static final ItemStack bone = new ItemBuilder(Material.BONE).setLore(ShopLore.getLore(200f)).build();
    public static final ItemStack stringItem = new ItemBuilder(Material.STRING).setLore(ShopLore.getLore(225f)).build();
    public static final ItemStack gunpowder = new ItemBuilder(Material.GUNPOWDER).setLore(ShopLore.getLore(225f)).build();
    public static final ItemStack spiderEye = new ItemBuilder(Material.SPIDER_EYE).setLore(ShopLore.getLore(250f)).build();
    public static final ItemStack membrane = new ItemBuilder(Material.PHANTOM_MEMBRANE).setLore(ShopLore.getLore(560f)).build();
    public static final ItemStack feather = new ItemBuilder(Material.FEATHER).setLore(ShopLore.getLore(200f)).build();
    public static final ItemStack leather = new ItemBuilder(Material.LEATHER).setLore(ShopLore.getLore(225f)).build();
    public static final ItemStack rabbitHide = new ItemBuilder(Material.RABBIT_HIDE).setLore(ShopLore.getLore(250f)).build();
}
