package com.occydaboss.skyblock.commands;

import com.fastasyncworldedit.core.FaweAPI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.IslandAPI;
import com.occydaboss.skyblock.util.Level;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class CommandIsland implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player) || args[0] == null) return false;

        Player player = (Player) sender;
        File schem = new File("plugins/skyblock/island.schem");
        World world = FaweAPI.getWorld("islands");

        if (args.length > 1) {
            if (args[0].equals("reset") && args[1] != null && args[1].equals("confirm")) {
                player.sendMessage(AddPrefix.addPrefix("Resetting Island..."));
                player.getInventory().clear();
                SkyBlock.economy.withdrawPlayer(player, SkyBlock.economy.getBalance(player));
                SkyBlock.worldBorderApi.resetWorldBorderToGlobal(player);
                Level.resetPlayerLevels(player);
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 62, 0));
                FindIterable<Document> iterable = SkyBlock.database.getCollection("islands")
                        .find(Filters.eq("_id", player.getUniqueId().toString()));
                Iterator iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    Document document = (Document) iterator.next();
                    int x = (int) document.get("x");
                    int z = (int) document.get("z");

                    BlockVector3 pos1 = BlockVector3.at(x - 50, 0, z - 50);
                    BlockVector3 pos2 = BlockVector3.at(x + 50, 256, z + 50);

                    for (int x1 = pos1.getX(); x1 <= pos2.getBlockX(); x1++) {
                        for (int y1 = pos1.getY(); y1 <= pos2.getBlockY(); y1++) {
                            for (int z1 = pos1.getZ(); z1 <= pos2.getBlockZ(); z1++) {
                                Bukkit.getWorld("islands").getBlockAt(x1, y1, z1).setType(Material.AIR);
                            }
                        }
                    }
                }
                player.sendMessage(AddPrefix.addPrefix("Remove Island from Database..."));
                SkyBlock.database.getCollection("islands").deleteOne(Filters.eq("_id", player.getUniqueId().toString()));
                createIsland(player, schem, world);
            } else if (args[0].equals("tp") && Bukkit.getOfflinePlayer(Bukkit.getPlayer(args[1]).getUniqueId()) != null) {
                Location islandLocation = IslandAPI.getIslandCoordinates(Bukkit.getPlayer(args[1]));
                player.teleport(Bukkit.getWorld("islands").getHighestBlockAt(islandLocation.getBlockX(), islandLocation.getBlockZ()).getLocation());
                player.sendMessage(AddPrefix.addPrefix("Teleporting..."));

                SkyBlock.worldBorderApi.setBorder(player, 50, IslandAPI.getIslandCoordinates(Bukkit.getPlayer(args[1])));
            }
        } else if (args[0].equals("reset")) {
            player.sendMessage(AddPrefix.addPrefix("Are you sure you want to reset? This resets your balance, inventory and island, and resets your level back to 0. Type /is reset confirm to continue."));
        } else if (args[0].equals("create")) {
            createIsland(player, schem, world);
        } else if (args[0].equals("tp")) {
            Location islandLocation = IslandAPI.getIslandCoordinates(player);
            player.teleport(Bukkit.getWorld("islands").getHighestBlockAt(islandLocation.getBlockX(), islandLocation.getBlockZ()).getLocation());
            player.sendMessage(AddPrefix.addPrefix("Teleporting..."));

            SkyBlock.worldBorderApi.setBorder(player, 50, IslandAPI.getIslandCoordinates(player));
        } else return false;

        return true;
    }

    private void createIsland(Player player, File schem, World world) {
        player.sendMessage(AddPrefix.addPrefix("Finding Coordinates for Island..."));
        boolean breakLoop = false;
        for (int x = -28000000; x <= 28000000; x += 600) {
            for (int z = -28000000; z <= 28000000; z += 600) {
                if (Bukkit.getWorld("islands").getBlockAt(x, 61, z).getType() == Material.AIR) {
                    player.sendMessage(AddPrefix.addPrefix("Adding island to database..."));
                    MongoCollection<Document> collection = SkyBlock.database.getCollection("islands");
                    Document document = new Document("_id", player.getUniqueId().toString())
                            .append("x", x)
                            .append("z", z);
                    collection.insertOne(document);
                    SkyBlock.logger.info("Creating new island for player of UUID "
                            + player.getUniqueId() + " at coordinates " + x + ", " + z);

                    player.sendMessage(AddPrefix.addPrefix("Building island..."));
                    ClipboardFormat format = ClipboardFormats.findByFile(schem);
                    try (ClipboardReader reader = format.getReader(new FileInputStream(schem))) {
                        Clipboard clipboard = reader.read();
                        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
                            Operation operation = new ClipboardHolder(clipboard)
                                    .createPaste(editSession)
                                    .to(BlockVector3.at(x, 62, z + 3))
                                    .ignoreAirBlocks(false)
                                    .build();
                            Operations.complete(operation);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Inventory chest = ((Chest) Bukkit.getWorld("islands").getBlockAt(x + 1, 62, z).getState()).getBlockInventory();
                    ItemStack[] items = {
                            new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR), new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR), new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.LEATHER_CHESTPLATE),
                            new ItemStack(Material.CACTUS), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.POTATO),
                            new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                            new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.AIR), new ItemStack(Material.AIR),
                            new ItemStack(Material.AIR), new ItemStack(Material.AIR)
                    };
                    chest.setContents(items);

                    player.sendMessage(AddPrefix.addPrefix("Done! Teleporting..."));
                    player.teleport(new Location(Bukkit.getWorld(world.getName()), x, 62, z + 3));

                    SkyBlock.worldBorderApi.setBorder(player, 50, new Location(Bukkit.getWorld("islands"), x, 62, z));

                    breakLoop = true;
                    break;
                }
            }
            if (breakLoop) break;
        }
    }
}
