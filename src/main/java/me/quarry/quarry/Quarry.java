package me.quarry.quarry;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.block.Chest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class Quarry extends JavaPlugin {
    public Quarry quarryThis=this;

    Thread mine;
    quarryMap map=new quarryMap();
    customMap custMap=new customMap();

    @Override
    public void onEnable() {


        // Plugin startup logic
        ItemStack result= new ItemStack(Material.DIAMOND_PICKAXE,1);

        ItemMeta meta= result.getItemMeta();

        meta.setDisplayName(ChatColor.RED +"Big Dig");

        result.setItemMeta(meta);

        result.addEnchantment(Enchantment.DIG_SPEED,5);
        result.addEnchantment(Enchantment.MENDING,1);
        result.addEnchantment(Enchantment.DURABILITY,3);
        result.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3);

        NamespacedKey key=new NamespacedKey(this, "Big_Dig");

        ShapedRecipe recipe=new ShapedRecipe(key,result);
        recipe.shape(
                "DDD",
                " P ",
                "   "
        );
        recipe.setIngredient('D',Material.DIAMOND_BLOCK);
        recipe.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe);
        //for magic stick

        ItemStack stick= new ItemStack(Material.STICK,1);

        ItemMeta meta3= stick.getItemMeta();

        meta3.setDisplayName(ChatColor.RED +"Marker");

        stick.setItemMeta(meta3);



        NamespacedKey key3=new NamespacedKey(this, "Marker");

        ShapedRecipe recipe3=new ShapedRecipe(key3,stick);
        recipe3.shape(
                " P ",
                " F ",
                "   "
        );
        recipe3.setIngredient('F',Material.STICK);
        recipe3.setIngredient('P',Material.OBSIDIAN);

        Bukkit.addRecipe(recipe3);
        //testing for new quarry
        ItemStack quarry= new ItemStack(Material.FURNACE,1);

        ItemMeta meta2= quarry.getItemMeta();

        meta2.setDisplayName(ChatColor.RED +"Quarry");

        quarry.setItemMeta(meta2);



        NamespacedKey key2=new NamespacedKey(this, "Quarry");

        ShapedRecipe recipe2=new ShapedRecipe(key2,quarry);
        recipe2.shape(
                " P ",
                " F ",
                "   "
        );
        recipe2.setIngredient('F',Material.FURNACE);
        recipe2.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe2);
//------------------------------------------------------------------------
        ItemStack custom= new ItemStack(Material.STICK,1);

        ItemMeta meta4= custom.getItemMeta();

        meta4.setDisplayName(ChatColor.RED +"custom marker");

        custom.setItemMeta(meta4);



        NamespacedKey key4=new NamespacedKey(this, "Custom");

        ShapedRecipe recipe4=new ShapedRecipe(key4,custom);
        recipe4.shape(
                " P ",
                " F ",
                "   "
        );
        recipe4.setIngredient('F',Material.STICK);
        recipe4.setIngredient('P',Material.STICK);

        Bukkit.addRecipe(recipe4);

//        ---------------------------------------------------------------------------
        ItemStack customQuarry= new ItemStack(Material.FURNACE,1);

        ItemMeta meta5= custom.getItemMeta();

        meta5.setDisplayName(ChatColor.RED +"Custom Quarry");

        customQuarry.setItemMeta(meta5);



        NamespacedKey key5=new NamespacedKey(this, "Custom_Quarry");

        ShapedRecipe recipe5=new ShapedRecipe(key5,customQuarry);
        recipe5.shape(
                "P P",
                " F ",
                "   "
        );
        recipe5.setIngredient('F',Material.FURNACE);
        recipe5.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe5);
//        -------------------------------------------------------------
        map=map.readMap();
        custMap=custMap.readMap();
        initializeRunningQuarrys();
    getServer().getPluginManager().registerEvents(new Miner(quarryThis),this);

    }

    private void initializeRunningQuarrys() {
        for(Map.Entry mapElement:quarryThis.map.map.entrySet()){
            Location loc=(Location)mapElement.getKey();
                minerData miner=quarryThis.map.map.get(loc);
                if(miner.isRunning){
                    quarryThis.runMiner(miner.quarryLocation,miner.chunk,miner.Id);
//
                }


        }
    }
    mineCustom customMiner;
    public void runCustom(Location quarryLoc){

        this.customMiner=new mineCustom(this,quarryLoc);
        Thread thread=new Thread(this.customMiner);
        this.customMiner.setThread(thread);
        thread.start();

    }






    //makes thread for a miner
    mineChunk miner;

    public void runMiner(Location quarryLoc, Chunk breakChun, Player user, int id){
        this.miner=new mineChunk(quarryLoc,breakChun,user,this,id);
        Thread thread=new Thread(this.miner);
        this.miner.setThread(thread);
        thread.start();
    }
    public void runMiner(Location quarryLoc, Chunk breakChun,int id){
        this.miner=new mineChunk(quarryLoc,breakChun,this,id);
        Thread thread=new Thread(this.miner);
        this.miner.setThread(thread);
        thread.start();
    }
    public void updateMinerStatus(boolean status){
        this.miner.setRunning(status);
    }
    //connects to main thread to updates blocks
    public void changeBlock(Chunk chunl,int x,int y,int z,Location quarryLocation){
        BukkitRunnable runner=new BukkitRunnable() {
            @Override
            public void run() {
                Block bloc=chunl.getBlock(x,y,z);
//                Bukkit.getServer().getWorld("world").loadChunk(chunl);
                if(map.map.get(quarryLocation).chestLocation!=null){
                    Location chestLoc=map.map.get(quarryLocation).chestLocation;
                    if(chestLoc.getBlock().getType()!=Material.CHEST){
                        map.map.get(quarryLocation).chestLocation=null;
                    }else {
//                    Block chestBlock=chestLoc.getBlock();
//                    BlockState chestState = chestBlock.getState();

                        Chest chest = (Chest) chestLoc.getBlock().getState();
                        Inventory inventory=chest.getInventory();
                        //------------------------------------------------
                        //https://bukkit.org/threads/checking-if-a-chest-is-full.51617/
//                        Credit to bergerkiller
                        //-------------------------------------------------
                        boolean hasEmptySlot = false;
                        for (ItemStack stack : inventory.getContents()) {
                            if (stack == null) {
                                hasEmptySlot = true;
                                break;
                            }
                        }
                        //method b: if it contains room for any sort of item
                        int foundcount = 0;
                        Collection<ItemStack> drops2 = bloc.getDrops();
                        for (ItemStack drop3 : drops2) {
                            ItemStack itemToAdd = drop3;
                            foundcount = itemToAdd.getAmount();
                            for (ItemStack stack : inventory.getContents()) {
                                if (stack == null) foundcount -= itemToAdd.getMaxStackSize();
                                else if(stack.getType() == itemToAdd.getType()) {
                                    if (stack.getDurability() == itemToAdd.getDurability()) {
                                        foundcount -= itemToAdd.getMaxStackSize() - stack.getAmount();
                                    }
                                }
                            }
                        }
                        boolean canContainitem = foundcount <= 0;
                        //-----------------------------------------------------------------------

                        if(canContainitem||hasEmptySlot) {
                            Collection<ItemStack> drops = bloc.getDrops();
                            for (ItemStack drop : drops) {
                                chest.getInventory().addItem(drop);
//                            map.map.get(quarryLocation).chest.customChest.addItem(drop);
                            }
                            bloc.setType(Material.AIR);
                        }else {
                            bloc.breakNaturally();
                        }
                    }

                }
                bloc.breakNaturally();
//                bloc.getWorld().unloadChunk(chunl);
//                bloc.getWorld().loadChunk(chunl);
            }
        };runner.runTask(this);

//        Bukkit.broadcastMessage("Updating block");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        map.saveMap();
        custMap.saveMap();

    }


    public void changeBlock(int x, int y, int z) {
        BukkitRunnable runner=new BukkitRunnable() {
            @Override
            public void run() {
                Block bloc=Bukkit.getServer().getWorld("world").getBlockAt(x,y,z);
//                Bukkit.getServer().getWorld("world").loadChunk(chunl);

                        //TODO add chest is full to switch to breaking naturall again

                        //TODO---------------------------------------------------------

                bloc.breakNaturally();
//                bloc.getWorld().unloadChunk(chunl);
//                bloc.getWorld().loadChunk(chunl);
            }
        };runner.runTask(this);

    }
}
