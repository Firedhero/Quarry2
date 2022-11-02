package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class funStuff {
    Quarry context;
    funStuff(){
        
    }
    
    funStuff(Quarry quarry){
        context=quarry;
        addStuffToServer();
    }

    private void addStuffToServer() {
        addRedStoneChest();
        addFoodChest();
        addSuperPick();
        addSuperChest();
        
    }

    private void addSuperChest() {
        ItemStack chest= new ItemStack(Material.CHEST,1);
        ItemMeta meta= chest.getItemMeta();
        meta.setDisplayName(ChatColor.RED +"Quarry Chest");
        ArrayList<String>lore=new ArrayList<>();
        lore.add("This chest is for Quarry Deposit");
        meta.setLore(lore);
        chest.setItemMeta(meta);
        NamespacedKey key=new NamespacedKey(context, "Quarry Chest");
        ShapedRecipe recipe=new ShapedRecipe(key,chest);
        recipe.shape(
                "WWW",
                "WCW",
                "WWW"
        );
        recipe.setIngredient('C',Material.CHEST);
        recipe.setIngredient('W',Material.OAK_WOOD);
        Bukkit.addRecipe(recipe);
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

        NamespacedKey key=new NamespacedKey(context, "Big_Dig");

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
