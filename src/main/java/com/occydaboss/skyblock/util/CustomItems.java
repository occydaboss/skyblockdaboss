package com.occydaboss.skyblock.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class CustomItems {
    public static final ItemStack enchantedDiamond = new ItemBuilder(Material.DIAMOND).setGlow(true).setDisplayName(ChatColor.AQUA + "Enchanted Diamond").build();
    public static final ItemStack meatOfTheUndead = new ItemBuilder(Material.PORKCHOP).setGlow(true).setDisplayName(ChatColor.DARK_PURPLE + "Meat of the Undead").build();
    public static final ItemStack bobSummon = new ItemBuilder(Material.ROTTEN_FLESH).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Enchanted Flesh").setGlow(true).setLore(ChatColor.WHITE + "Summons Bob").setAmount(1).build();

    public static ItemStack bobSword ()
    {
        ItemStack i = new ItemBuilder(Material.NETHERITE_SWORD).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Sword of the Undead").addItemFlag(ItemFlag.HIDE_UNBREAKABLE).setUnbreakable(true).setGlow(true).build();
        ItemMeta meta = i.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "bob_sword_speed", 1.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "bob_sword_damage", 15, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        i.setItemMeta(meta);
        return i;
    }

    public static ItemStack bobAxe ()
    {
        ItemStack i = new ItemBuilder(Material.DIAMOND_AXE).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Axe of the Undead").addItemFlag(ItemFlag.HIDE_UNBREAKABLE).setUnbreakable(true).setGlow(true).build();
        ItemMeta meta = i.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "bob_axe_speed", 0.8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "bob_axe_damage", 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        i.setItemMeta(meta);
        return i;
    }

    public static ItemStack bobHelmet ()
    {
        ItemStack i = new ItemBuilder(Material.DIAMOND_HELMET).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Helmet of the Undead").addItemFlag(ItemFlag.HIDE_UNBREAKABLE).setUnbreakable(true).setGlow(true).build();
        ItemMeta meta = i.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "bob_helmet_defense", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "bob_helmet_points", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        i.setItemMeta(meta);
        return i;
    }

    public static ItemStack bobChestplate ()
    {
        ItemStack i = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Chestplate of the Undead").addItemFlag(ItemFlag.HIDE_UNBREAKABLE).setUnbreakable(true).setGlow(true).build();
        ItemMeta meta = i.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "bob_chestplate_defense", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "bob_chest_points", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        i.setItemMeta(meta);
        return i;
    }

    public static ItemStack bobLeggings ()
    {
        ItemStack i = new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Leggings of the Undead").addItemFlag(ItemFlag.HIDE_UNBREAKABLE).setUnbreakable(true).setGlow(true).build();
        ItemMeta meta = i.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "bob_leggings_defense", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "bob_legs_points", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        i.setItemMeta(meta);
        return i;
    }

    public static ItemStack bobBoots ()
    {
        ItemStack i = new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Boots of the Undead").addItemFlag(ItemFlag.HIDE_UNBREAKABLE).setUnbreakable(true).setGlow(true).build();
        ItemMeta meta = i.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "bob_boots_defense", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "bob_boots_points", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        i.setItemMeta(meta);
        return i;
    }
}
