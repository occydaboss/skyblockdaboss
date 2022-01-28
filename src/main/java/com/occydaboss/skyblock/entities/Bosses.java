package com.occydaboss.skyblock.entities;

import com.occydaboss.skyblock.util.CustomItems;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

public class Bosses {
    public static LivingEntity bobBoss(Location location) {
        LivingEntity bob = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        bob.setCustomName(ChatColor.BOLD.toString() + ChatColor.AQUA.toString() + "Bob");
        AttributeInstance health = bob.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        AttributeInstance speed = bob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        health.setBaseValue(150d);
        speed.setBaseValue(0.223d);
        ((Zombie) bob).setAdult();

        bob.getEquipment().setHelmet(CustomItems.bobHelmet());
        bob.getEquipment().setChestplate(CustomItems.bobChestplate());
        bob.getEquipment().setLeggings(CustomItems.bobLeggings());
        bob.getEquipment().setBoots(CustomItems.bobBoots());
        bob.getEquipment().setItemInMainHand(CustomItems.bobSword());

        return bob;
    }
}
