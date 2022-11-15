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
        addQuarryChest(context);
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
//    TODO add custom Quarry Id names for custom stick names
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
    private void addQuarryChest(Quarry context) {
        ItemStack quarryChest= new ItemStack(Material.CHEST,1);
        ItemMeta meta= quarryChest.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD +"Quarry Chest");
        quarryChest.setItemMeta(meta);

        NamespacedKey key=new NamespacedKey(context, "Quarry_Chest");
        ShapedRecipe recipe=new ShapedRecipe(key,quarryChest);
        recipe.shape(
                "FFF",
                "F F",
                "FFF"
        );
        recipe.setIngredient('F',Material.CHEST);

        Bukkit.addRecipe(recipe);

    }
}
