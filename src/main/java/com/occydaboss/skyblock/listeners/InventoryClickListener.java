package com.occydaboss.skyblock.listeners;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.guis.*;
import com.occydaboss.skyblock.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

import static com.occydaboss.skyblock.util.ShopItems.*;

public class InventoryClickListener implements Listener {
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
                    switch (Level.getMainLevel(player)) {
                        case 0:
                            if (SkyBlock.economy.has(player, 1000)) {
                                Level.resetSubLevels(player);
                                Level.setMainLevel(player, Level.getMainLevel(player) + 1);
                                player.sendMessage(AddPrefix.addPrefix("You are now level " + Level.getMainLevel(player)));
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                                player.closeInventory();
                                SkyBlock.economy.withdrawPlayer(player, 1000);
                            }
                            break;
                        case 1:
                            if (SkyBlock.economy.has(player, 10000)) {
                                Level.resetSubLevels(player);
                                Level.setMainLevel(player, Level.getMainLevel(player) + 1);
                                player.sendMessage(AddPrefix.addPrefix("You are now level " + Level.getMainLevel(player)));
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1);
                                player.closeInventory();
                                SkyBlock.economy.withdrawPlayer(player, 10000);
                            }
                            break;
                    }
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
}
