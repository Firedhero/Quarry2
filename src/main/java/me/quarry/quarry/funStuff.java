package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class funStuff {
    Quarry quarry;
    funStuff(){
        
    }
    
    funStuff(Quarry context){
        quarry=context;
        addStuffToServer();
    }

    private void addStuffToServer() {
        addRedStoneChest();
        addFoodChest();
        addSuperPick();
        
    }

    private void addSuperPick() {
        ItemStack result= new ItemStack(Material.DIAMOND_PICKAXE,1);

        ItemMeta meta= result.getItemMeta();

        meta.setDisplayName(ChatColor.RED +"Big Dig");

        result.setItemMeta(meta);

        result.addEnchantment(Enchantment.DIG_SPEED,5);
        result.addEnchantment(Enchantment.MENDING,1);
        result.addEnchantment(Enchantment.DURABILITY,3);
        result.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3);

        NamespacedKey key=new NamespacedKey(quarry, "Big_Dig");

        ShapedRecipe recipe=new ShapedRecipe(key,result);
        recipe.shape(
                "DDD",
                " P ",
                "   "
        );
        recipe.setIngredient('D',Material.DIAMOND_BLOCK);
        recipe.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe);
    }

    private void addFoodChest() {
    }

    private void addRedStoneChest() {
    }
}
