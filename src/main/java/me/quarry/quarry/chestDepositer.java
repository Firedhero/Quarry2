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
import java.util.Objects;

import static java.lang.Thread.sleep;

public class chestDepositer implements Runnable {
    minerData context;
    chestDepositer(minerData context){


    }

    @Override
    public void run() {
//        TODO add loop, add notify method that alerts thread when items removed from chest otherwise it permanently sleeps saving resources
//        try {
////            deposit();
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
    }
     synchronized void deposit() throws InterruptedException, IOException {
//        while (context.chestLocation==null)
//            wait();
//
//        Material blockType=context.savedItems.takeFromItemHashMap();
//         while (!checkSpace(blockType))
//             wait();
//         while (blockType!=Material.AIR){
//             updateChestInventory(blockType);
//             blockType=context.savedItems.takeFromItemHashMap();
//             sleep(500);
//             deposit();
//
//         }

    }





}
