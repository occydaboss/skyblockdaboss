package com.occydaboss.skyblock.listeners;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.entities.Bosses;
import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.CustomItems;
import com.occydaboss.skyblock.util.IslandAPI;
import com.occydaboss.skyblock.util.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.Random;

public class BossListeners implements Listener {
    HashMap<Player, BossBar> bobBar1 = new HashMap<>();
    HashMap<Entity, Player> bobBar2 = new HashMap<>();
    HashMap<Player, Entity> bobBar3 = new HashMap<>();

    @EventHandler
    public void onRightClickBossSummon(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (Level.getMainLevel(player) >= 1) {
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
                ((LivingEntity) e.getEntity()).setHealth(-1f);
            }
        }
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
}
