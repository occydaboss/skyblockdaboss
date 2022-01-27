package com.occydaboss.skyblock.recipes;

import com.occydaboss.skyblock.SkyBlock;
import com.occydaboss.skyblock.util.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {
    public static void enchantedDiamondRecipe() {
        NamespacedKey key = new NamespacedKey(SkyBlock.getInstance(), "enchanted_diamond");
        ShapedRecipe recipe = new ShapedRecipe(key, CustomItems.enchantedDiamond);
        recipe.shape(" D ", "DDD", " D ");
        recipe.setIngredient('D', new RecipeChoice.ExactChoice(new ItemStack(Material.DIAMOND)));
        Bukkit.addRecipe(recipe);
    }

    public static void undeadFleshRecipe() {
        NamespacedKey key = new NamespacedKey(SkyBlock.getInstance(), "undead_flesh");
        ShapedRecipe recipe = new ShapedRecipe(key, CustomItems.meatOfTheUndead);
        recipe.shape("BFB", "FSF", "BFB");
        recipe.setIngredient('B', new RecipeChoice.ExactChoice(new ItemStack(Material.BONE)));
        recipe.setIngredient('F', new RecipeChoice.ExactChoice(new ItemStack(Material.ROTTEN_FLESH)));
        recipe.setIngredient('S', new RecipeChoice.ExactChoice(new ItemStack(Material.SPIDER_EYE)));
        Bukkit.addRecipe(recipe);
    }

    public static void bobSummonRecipe() {
        NamespacedKey key = new NamespacedKey(SkyBlock.getInstance(), "bob_summon");
        ShapedRecipe recipe = new ShapedRecipe(key, CustomItems.bobSummon);
        recipe.shape("EFE", "FEF", "EFE");
        RecipeChoice flesh = new RecipeChoice.ExactChoice(CustomItems.meatOfTheUndead);
        RecipeChoice enchantedDiamond = new RecipeChoice.ExactChoice(CustomItems.enchantedDiamond);
        recipe.setIngredient('F', flesh);
        recipe.setIngredient('E', enchantedDiamond);
        Bukkit.addRecipe(recipe);
    }
}
