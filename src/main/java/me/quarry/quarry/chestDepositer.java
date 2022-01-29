package me.quarry.quarry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class chestDepositer implements Runnable {
    minerData context;
    final Thread thread;
    chestDepositer(minerData context){
        this.context=context;
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
//        TODO add loop, add notify method that alerts thread when items removed from chest otherwise it permanently sleeps saving resources
        try {
            deposit();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
     synchronized void deposit() throws InterruptedException, IOException {
        while (context.chestLocation==null)
            wait();

        Material blockType=context.savedItems.takeFromItemHashMap();
         while (!checkSpace(blockType))
             wait();
         while (blockType!=null){
             updateChestInventory(blockType);
             blockType=context.savedItems.takeFromItemHashMap();
             sleep(500);
             deposit();

         }

    }
    private boolean checkSpace(Material blockType){
        final boolean[] isSpace = {false};
        BukkitRunnable runner2=new BukkitRunnable() {
            @Override
            public void run() {
                if (checkChestSpace(blockType)) {
                    isSpace[0] =true;
                }
            }

        };runner2.runTask(context.context);
        return isSpace[0];
    }
    public boolean runningQuarryDepositor(Block block){
        if (checkChestSpace(block.getBlockData().getMaterial())) {
            Chest chest = (Chest) context.chestLocation.getBlock().getState();
            ItemStack itemStack = new ItemStack(Material.getMaterial(String.valueOf(block)), 1);
            chest.getInventory().addItem(itemStack);
            return true;
        }else{
            return false;
        }

    }
    public void updateChestInventory(Material blockType) {
        BukkitRunnable runner2=new BukkitRunnable() {
            @Override
            public void run() {
                if (checkChestSpace(blockType)) {
                    Chest chest = (Chest) context.chestLocation.getBlock().getState();
                    ItemStack itemStack = new ItemStack(Material.getMaterial(String.valueOf(blockType)), 1);
                    chest.getInventory().addItem(itemStack);
                }

            }
        };runner2.runTask(context.context);
    }


    private boolean checkChestSpace(Material savedBlock) {
        int count=1;
        Chest chest = (Chest) context.chestLocation.getBlock().getState();
        Inventory inventory=chest.getInventory();
        //------------------------------------------------
        //https://bukkit.org/threads/checking-if-a-chest-is-full.51617/
        //         Credit to bergerkiller
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
        ItemStack itemToAdd=new ItemStack(savedBlock,count);
        foundcount = itemToAdd.getAmount();
        for (ItemStack stack : inventory.getContents()) {
            if (stack == null) foundcount -= itemToAdd.getMaxStackSize();
            else if(stack.getType() == itemToAdd.getType()) {
                if (stack.getDurability() == itemToAdd.getDurability()) {
                    foundcount -= itemToAdd.getMaxStackSize() - stack.getAmount();
                }
            }
        }

        boolean canContainitem = foundcount <= 0;
        //---------------------------end credit--------------------------------------------
        if(hasEmptySlot||canContainitem) {
            return true;
        }
        return false;
    }

}
