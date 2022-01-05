package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
    public Items(Quarry context) {
        addQuarry(context);
        addQuarryMarker(context);
        addCustomSizeQuarry(context);
        addCustomSizeMarker(context);
    }

    private void addCustomSizeMarker(Quarry context) {
        ItemStack customQuarry= new ItemStack(Material.FURNACE,1);
        ItemMeta meta= customQuarry.getItemMeta();
        meta.setDisplayName(ChatColor.RED +"Custom Quarry");
        customQuarry.setItemMeta(meta);
        NamespacedKey key=new NamespacedKey(context, "Custom_Quarry");

        ShapedRecipe recipe=new ShapedRecipe(key,customQuarry);
        recipe.shape(
                "P P",
                " F ",
                "   "
        );
        recipe.setIngredient('F',Material.FURNACE);
        recipe.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe);
    }

    private void addCustomSizeQuarry(Quarry context) {ItemStack custom= new ItemStack(Material.STICK,1);

        ItemMeta meta= custom.getItemMeta();
        meta.setDisplayName(ChatColor.RED +"custom marker");
        custom.setItemMeta(meta);
        NamespacedKey key=new NamespacedKey(context, "Custom");
        ShapedRecipe recipe=new ShapedRecipe(key,custom);
        recipe.shape(
                " P ",
                " F ",
                "   "
        );
        recipe.setIngredient('F',Material.STICK);
        recipe.setIngredient('P',Material.STICK);

        Bukkit.addRecipe(recipe);
    }

    private void addQuarryMarker(Quarry context) {
        ItemStack stick= new ItemStack(Material.STICK,1);
        ItemMeta meta= stick.getItemMeta();
        meta.setDisplayName(ChatColor.RED +"Marker");
        stick.setItemMeta(meta);
        NamespacedKey key=new NamespacedKey(context, "Marker");
        ShapedRecipe recipe=new ShapedRecipe(key,stick);
        recipe.shape(
                " P ",
                " F ",
                "   "
        );
        recipe.setIngredient('F',Material.STICK);
        recipe.setIngredient('P',Material.OBSIDIAN);
        Bukkit.addRecipe(recipe);
    }

    private void addQuarry(Quarry context) {
        ItemStack quarry= new ItemStack(Material.FURNACE,1);
        ItemMeta meta= quarry.getItemMeta();
        meta.setDisplayName(ChatColor.RED +"Quarry");
        quarry.setItemMeta(meta);
        NamespacedKey key=new NamespacedKey(context, "Quarry");
        ShapedRecipe recipe=new ShapedRecipe(key,quarry);
        recipe.shape(
                " P ",
                " F ",
                "   "
        );
        recipe.setIngredient('F',Material.FURNACE);
        recipe.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe);

    }
}
