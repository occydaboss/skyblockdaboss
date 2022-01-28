package com.occydaboss.skyblock;

import com.occydaboss.skyblock.entities.Bosses;
import com.occydaboss.skyblock.guis.*;
import com.occydaboss.skyblock.util.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BoundingBox;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Level.addPlayerToDatabase(e.getPlayer());

        SkyBlock.scoreboard.setTitle(e.getPlayer(), ChatColor.LIGHT_PURPLE + "SkyBlock");
        SkyBlock.updateScoreboard(e.getPlayer());
        SkyBlock.scoreboard.addPlayer(e.getPlayer());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyBlock.getInstance(), new Runnable() {
            @Override
            public void run() {
                SkyBlock.updateScoreboard(e.getPlayer());
            }
        }, 10, 10);

        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyBlock.getInstance(), new Runnable() {
            @Override
            public void run() {
                SkyBlock.worldBorderApi.setBorder(e.getPlayer(), 50, IslandAPI.getIslandCoordinates(e.getPlayer()));
            }
        }, 5);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        try {
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem().equals(filler)) {
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().equals(exit) || e.getCurrentItem().equals(cancel)) {
                player.closeInventory();
                if (Level.getMainLevel(player) == 0) {
                    player.openInventory(ShopMainGUI.getLevel0(player));
                } else if (Level.getMainLevel(player) == 1) {
                    player.openInventory(ShopMainGUI.getLevel1(player));
                }
                e.setCancelled(true);
                return;
            }

            // Buy Menu
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Buy Menu")) {
                ItemStack item = e.getView().getItem(13);
                float price = Float.parseFloat(e.getView().getItem(22).getItemMeta().getDisplayName().split("\\$")[1]);

                if (e.getCurrentItem().equals(rem1)) {
                    if (item.getAmount() - 1 < 1) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() - 1);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, price - Price.getPriceFromShopItem(item)));
                } else if (e.getCurrentItem().equals(rem10)) {
                    if (item.getAmount() - 10 < 1) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() - 10);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, price - (10 * Price.getPriceFromShopItem(item))));
                } else if (e.getCurrentItem().equals(add1)) {
                    if (item.getAmount() + 1 > 64) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() + 1);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, price + Price.getPriceFromShopItem(item)));
                } else if (e.getCurrentItem().equals(add10)) {
                    if (item.getAmount() + 10 > 64) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() + 10);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, price + (10 * Price.getPriceFromShopItem(item))));
                } else if (e.getCurrentItem().equals(set1)) {
                    item.setAmount(1);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, Price.getPriceFromShopItem(item)));
                } else if (e.getCurrentItem().equals(set64)) {
                    item.setAmount(64);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, (64 * Price.getPriceFromShopItem(item))));
                } else if (e.getCurrentItem().equals(confirm)) {
                    int amount = item.getAmount();
                    ItemStack cleanItem = new ItemStack(item.getType(), amount);

                    if (!(SkyBlock.economy.getBalance(player) - price <= 0)) {
                        if (InventoryTools.hasAvaliableSlot(player)) {
                            player.getInventory().addItem(cleanItem);
                            SkyBlock.economy.withdrawPlayer(player, price);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                            player.sendMessage(AddPrefix.addPrefix(ChatColor.GREEN + "Successfully bought " + amount + "x " + TextCase.setCase(String.valueOf(item.getType()).replaceAll("_", " ")) + " costing you $" + price));
                        } else {
                            player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient inventory space."));
                        }
                    } else {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient funds."));
                    }

                }
                e.setCancelled(true);
            }

            // Sell Menu
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Sell Menu")) {
                ItemStack item = e.getView().getItem(13);
                float price = Float.parseFloat(e.getView().getItem(22).getItemMeta().getDisplayName().split("\\$")[1]);

                if (e.getCurrentItem().equals(rem1)) {
                    if (item.getAmount() - 1 < 1) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() - 1);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, price - Price.getSellFromShopItem(item)));
                } else if (e.getCurrentItem().equals(rem10)) {
                    if (item.getAmount() - 10 < 1) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to below 1!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() - 10);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, price - (10 * Price.getSellFromShopItem(item))));
                } else if (e.getCurrentItem().equals(add1)) {
                    if (item.getAmount() + 1 > 64) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() + 1);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, price + Price.getSellFromShopItem(item)));
                } else if (e.getCurrentItem().equals(add10)) {
                    if (item.getAmount() + 10 > 64) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "Cannot set item amount to above 64!"));
                        player.closeInventory();
                        player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                        e.setCancelled(true);
                    }

                    item.setAmount(item.getAmount() + 10);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, price + (10 * Price.getSellFromShopItem(item))));
                } else if (e.getCurrentItem().equals(set1)) {
                    item.setAmount(1);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, Price.getSellFromShopItem(item)));
                } else if (e.getCurrentItem().equals(set64)) {
                    item.setAmount(64);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, (64 * Price.getSellFromShopItem(item))));
                } else if (e.getCurrentItem().equals(confirm)) {
                    int amount = item.getAmount();
                    ItemStack cleanItem = new ItemStack(item.getType(), amount);

                    if (InventoryTools.getAmount(player, cleanItem) >= amount) {
                        player.getInventory().removeItem(cleanItem);
                        SkyBlock.economy.depositPlayer(player, Math.round((price) * 100.0) / 100.0);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.GREEN + "Successfully sold " + amount + "x " + TextCase.setCase(String.valueOf(item.getType()).replaceAll("_", " ")) + " giving you $" + price));
                    } else {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient items."));
                    }
                }
                e.setCancelled(true);
            }

            // Level Up Menu
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Level Up")) {
                if (e.getCurrentItem().getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) == 0) {
                        Level.setMiningLevel(player, 0);
                        Level.setFarmingLevel(player, 0);
                    }
                    Level.setMainLevel(player, Level.getMainLevel(player) + 1);
                    player.sendMessage(AddPrefix.addPrefix("You are now level " + Level.getMainLevel(player)));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                }

                e.setCancelled(true);
            }


            // Main Menu
            if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Main Menu")) {
                if (e.getCurrentItem().equals(blocksButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) == 0) {
                        player.openInventory(BlocksGUI.getLevel0(player));
                    } else if (Level.getMainLevel(player) == 1) {
                        player.openInventory(BlocksGUI.getLevel1(player));
                    }
                } else if (e.getCurrentItem().equals(materialsButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) <= 1) {
                        player.openInventory(MaterialsGUI.getLevel0(player));
                    }
                } else if (e.getCurrentItem().equals(farmingButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) <= 1) {
                        player.openInventory(FarmingGUI.getLevel0(player));
                    }
                } else if (e.getCurrentItem().equals(redstoneButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) >= 1) {
                        player.openInventory(RedstoneGUI.getLevel1(player));
                    }
                } else if (e.getCurrentItem().equals(miscButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) >= 1) {
                        player.openInventory(MiscGUI.getLevel1(player));
                    }
                } else if (e.getCurrentItem().equals(mobDropsButton)) {
                    player.closeInventory();
                    if (Level.getMainLevel(player) >= 1) {
                        player.openInventory(MobDropsGUI.getLevel1(player));
                    }
                }
                e.setCancelled(true);
            }

            // Buy/Sell Items
            if (
                    ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Blocks") ||
                            ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Materials") ||
                            ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Farming") ||
                            ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Redstone") ||
                            ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Miscellaneous") ||
                            ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Mob Drops")
            ) {
                if (e.getClick().isShiftClick()) {
                    ItemStack cleanItem = new ItemStack(e.getCurrentItem().getType());
                    float price = Price.getSellFromShopItem(e.getCurrentItem());
                    int amount = InventoryTools.getAmount(player, cleanItem);

                    if (amount <= 0) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You do not have sufficient items."));
                    } else {
                        player.getInventory().removeItem(new ItemStack(cleanItem.getType(), amount));
                        SkyBlock.economy.depositPlayer(player, Math.round((amount * price) * 100.0) / 100.0);

                        player.sendMessage(AddPrefix.addPrefix(ChatColor.GREEN + "Successfully sold " + amount + "x " + TextCase.setCase(String.valueOf(cleanItem.getType()).replaceAll("_", " ")) + " giving you $" + amount * price));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    }

                    e.setCancelled(true);
                } else if (e.getClick().isLeftClick()) {
                    float price = Price.getPriceFromShopItem(e.getCurrentItem());
                    ItemMeta meta = e.getCurrentItem().getItemMeta();
                    meta.setLore(Collections.singletonList(meta.getLore().get(0)));
                    ItemStack item = e.getCurrentItem();
                    item.setItemMeta(meta);

                    player.closeInventory();
                    player.openInventory(BuySellGUI.getBuyMenu(player, item, price));
                    e.setCancelled(true);
                } else if (e.getClick().isRightClick()) {
                    ItemStack item = e.getCurrentItem();
                    if (e.getClickedInventory() == MiscGUI.getLevel1(player)) {
                        player.sendMessage(AddPrefix.addPrefix(ChatColor.RED + "You cannot sell this item."));
                        e.setCancelled(true);
                    }
                    float price = Price.getSellFromShopItem(e.getCurrentItem());
                    ItemMeta meta = e.getCurrentItem().getItemMeta();
                    meta.setLore(Collections.singletonList(meta.getLore().get(0)));
                    item.setItemMeta(meta);
                    player.closeInventory();
                    player.openInventory(BuySellGUI.getSellMenu(player, item, price));
                    e.setCancelled(true);
                }
            }
        } catch (NullPointerException npe) {
            return;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        int level = Level.getMainLevel(player);
        Block block = e.getBlock();

        if (level == 0) {
            if (block.getType().equals(Material.COBBLESTONE)) {
                if (Level.getMiningLevel(player) < 100) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "+5 mining xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setMiningLevel(player, Level.getMiningLevel(player) + 5);
                }
            } else if (block.getType().equals(Material.SUGAR_CANE)) {
                if (Level.getFarmingLevel(player) < 100) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "+5 farming xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setFarmingLevel(player, Level.getFarmingLevel(player) + 5);
                }
            }
        } else if (level == 1) {
            if (block.getType().equals(Material.COBBLESTONE)) {
                if (Level.getMiningLevel(player) < 1000) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "+5 mining xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setMiningLevel(player, Level.getMiningLevel(player) + 5);
                }
            } else if (block.getType().equals(Material.SANDSTONE)) {
                if (Level.getMiningLevel(player) < 1000) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GRAY + "+7.5 mining xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setMiningLevel(player, Level.getMiningLevel(player) + 7.5);
                }
            } else if (block.getType().equals(Material.MELON) || block.getType().equals(Material.PUMPKIN)) {
                if (Level.getMiningLevel(player) < 1000) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.YELLOW + "+5 farming xp"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                    Level.setFarmingLevel(player, Level.getFarmingLevel(player) + 5);
                }
            }
        }
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        Material type = event.getBlock().getType();
        if (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER || type == Material.LAVA || type == Material.LEGACY_STATIONARY_LAVA) {
            Block b = event.getToBlock();
            if (b.getType() == Material.AIR) {
                if (generatesCobble(type, b)) {
                    event.setCancelled(true);

                    Random random = new Random();
                    int randomChance = random.nextInt(100);

                    Player player = b.getWorld().getPlayers().get(0);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(SkyBlock.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            b.getWorld().playSound(b.getLocation(),
                                    Sound.BLOCK_LAVA_EXTINGUISH,
                                    SoundCategory.BLOCKS,
                                    0.5F,
                                    1);


                            for (int counter = 0; counter < 8; ++counter) {
                                b.getWorld().spawnParticle(Particle.SMOKE_LARGE,
                                        b.getX() + Math.random(),
                                        b.getY() + 1 + Math.random(),
                                        b.getZ() + Math.random(),
                                        1,
                                        0,
                                        0,
                                        0,
                                        0);
                            }

                            if (Level.getMainLevel(player) == 0) {
                                if (randomChance <= 40) {
                                    b.setType(Material.COBBLESTONE);
                                } else if (randomChance <= 60) {
                                    b.setType(Material.SANDSTONE);
                                } else if (randomChance <= 75) {
                                    b.setType(Material.COAL_ORE);
                                } else if (randomChance <= 100) {
                                    b.setType(Material.IRON_ORE);
                                }
                            } else if (Level.getMainLevel(player) == 1) {
                                if (randomChance <= 30) {
                                    b.setType(Material.COBBLESTONE);
                                } else if (randomChance <= 50) {
                                    b.setType(Material.SANDSTONE);
                                } else if (randomChance <= 65) {
                                    b.setType(Material.COAL_ORE);
                                } else if (randomChance <= 75) {
                                    b.setType(Material.IRON_ORE);
                                } else if (randomChance <= 85) {
                                    b.setType(Material.GOLD_ORE);
                                } else if (randomChance <= 90) {
                                    b.setType(Material.LAPIS_ORE);
                                } else if (randomChance <= 93) {
                                    b.setType(Material.REDSTONE_ORE);
                                } else if (randomChance <= 97) {
                                    b.setType(Material.EMERALD_ORE);
                                } else if (randomChance <= 100) {
                                    b.setType(Material.DIAMOND_ORE);
                                }
                            } else if (Level.getMainLevel(player) == 2) {
                                if (randomChance <= 20) {
                                    b.setType(Material.COBBLESTONE);
                                } else if (randomChance <= 30) {
                                    b.setType(Material.SANDSTONE);
                                } else if (randomChance <= 40) {
                                    b.setType(Material.RED_SANDSTONE);
                                } else if (randomChance <= 65) {
                                    b.setType(Material.COAL_ORE);
                                } else if (randomChance <= 75) {
                                    b.setType(Material.IRON_ORE);
                                } else if (randomChance <= 85) {
                                    b.setType(Material.GOLD_ORE);
                                } else if (randomChance <= 90) {
                                    b.setType(Material.LAPIS_ORE);
                                } else if (randomChance <= 93) {
                                    b.setType(Material.REDSTONE_ORE);
                                } else if (randomChance <= 97) {
                                    b.setType(Material.EMERALD_ORE);
                                } else if (randomChance <= 100) {
                                    b.setType(Material.DIAMOND_ORE);
                                }
                            }
                        }
                    }, 5);
                }
            }
        }
    }

    /*
    Boss shit
     */

    HashMap<Player, BossBar> bobBar1 = new HashMap<>();
    HashMap<Entity, Player> bobBar2 = new HashMap<>();
    HashMap<Player, Entity> bobBar3 = new HashMap<>();

    @EventHandler
    public void onRightClickBossSummon(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();
        if (itemStack == null || itemStack.getItemMeta().displayName() == null) return;
        if (itemStack.getItemMeta().displayName().equals(CustomItems.bobSummon.getItemMeta().displayName())) {
            if (e.getClickedBlock() == null) return;
            Location spawnLocation = new Location(
                    e.getClickedBlock().getWorld(),
                    e.getClickedBlock().getX(),
                    e.getClickedBlock().getY() + 1,
                    e.getClickedBlock().getZ());
            if (IslandAPI.withinIsland(player, spawnLocation)) {
                BoundingBox box = BoundingBox.of(IslandAPI.getIslandBounds(player)[0], IslandAPI.getIslandBounds(player)[1]);
                for (Entity entity : e.getClickedBlock().getWorld().getNearbyEntities(box)) {
                    if (entity.getCustomName() != null) {
                        if (entity.getName().contains("Bob")) {
                            player.sendMessage(AddPrefix.addPrefix("Already a boss alive!"));
                            return;
                        }
                    }
                }
                LivingEntity boss = Bosses.bobBoss(spawnLocation);
                BossBar bossbar = Bukkit.createBossBar(
                        ChatColor.BOLD.toString() + ChatColor.AQUA.toString() + "Bob",
                        BarColor.BLUE,
                        BarStyle.SOLID
                );
                bossbar.addPlayer(player);
                bobBar1.put(player, bossbar);
                bobBar2.put(boss, player);
                bobBar3.put(player, boss);

                e.getClickedBlock().getWorld().strikeLightningEffect(boss.getLocation());
                e.setCancelled(true);
            } else {
                player.sendMessage(AddPrefix.addPrefix("Cannot spawn boss here!"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBossSummon(EntitySpawnEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyBlock.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (e.getEntity().getName().contains("Bob")) {
                    ((LivingEntity) e.getEntity()).setHealth(150d);
                }
            }
        }, 1);
    }

    @EventHandler
    public void onBossHurt(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        Player player = (Player) e.getDamager();
        if (e.getEntity().getName().contains("Bob")) {
            bobBar1.get(player).setProgress(((LivingEntity) e.getEntity()).getHealth() / 150d);
        }
    }

    @EventHandler
    public void onBossInVoid(EntityDamageEvent e) {
        if (e.getEntity().getName().contains("Bob")) {
            if (e.getEntity().getLocation().getBlockY() < 0) {
                bobBar1.get(bobBar2.get(e.getEntity())).removePlayer(bobBar2.get(e.getEntity()));
                bobBar1.remove(bobBar2.get(e.getEntity()));
                bobBar3.remove(bobBar2.get(e.getEntity()));
                bobBar2.remove(e.getEntity());
                ((LivingEntity) e.getEntity()).setHealth(0);
            }
        }
        return;
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent e) {
        LivingEntity boss = e.getEntity();
        if (boss.getName().contains("Bob")) {
            boss.getWorld().strikeLightningEffect(boss.getLocation());
            e.getDrops().clear();
            Random random = new Random();
            int equipmentDrop = random.nextInt(10);
            int fleshDrop = random.nextInt(3);
            switch (equipmentDrop) {
                case 0:
                    e.getDrops().add(CustomItems.bobSword());
                    break;
                case 1:
                    e.getDrops().add(CustomItems.bobHelmet());
                    break;
                case 2:
                    e.getDrops().add(CustomItems.bobChestplate());
                    break;
                case 3:
                    e.getDrops().add(CustomItems.bobLeggings());
                    break;
                case 4:
                    e.getDrops().add(CustomItems.bobBoots());
                    break;
            }
            ItemStack flesh = CustomItems.meatOfTheUndead;
            flesh.setAmount(fleshDrop + 1);
            e.getDrops().add(flesh);
            Level.setCombatLevel(bobBar2.get(e.getEntity()), Level.getCombatLevel(bobBar2.get(e.getEntity())) + 1d);

            bobBar2.get(e.getEntity()).sendMessage(AddPrefix.addPrefix("Bob has been defeated!"));
            bobBar1.get(bobBar2.get(e.getEntity())).removePlayer(bobBar2.get(e.getEntity()));
            bobBar1.remove(bobBar2.get(e.getEntity()));
            bobBar3.remove(bobBar2.get(e.getEntity()));
            bobBar2.remove(e.getEntity());
        }
    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b) {
        Material mirrorID1 = (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER ? Material.LAVA : Material.WATER);
        Material mirrorID2 = (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER ? Material.LEGACY_STATIONARY_LAVA : Material.LEGACY_STATIONARY_WATER);
        for (BlockFace face : faces) {
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1 || r.getType() == mirrorID2) {
                return true;
            }
        }
        return false;
    }
}
