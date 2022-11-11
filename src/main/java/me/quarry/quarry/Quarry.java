package me.quarry.quarry;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public final class Quarry extends JavaPlugin {
    public Quarry quarryThis=this;

    Thread mine;
    quarryMap map=new quarryMap();
//    savedChestItems savedItems=new savedChestItems();

    @Override
    public void onEnable() {
        
//      TODO add an automatic tree farmer possible another plugin
        /*
        TODO save what player placed what quarry
         add a popup menu for what quarrys a player has placed
         add a way for player to pick which quarry from list instead of the stick
         */

        // Plugin startup logic

        //Adds the Items
        new Items(this);

        map=map.readMap();
        savedDataFilesExist();

        getServer().getPluginManager().registerEvents(new eventListner(quarryThis),this);

    }

    private void savedDataFilesExist() {
        //file checks
        File file = new File("plugins/hashMapLocations.txt");
        File file2 = new File("plugins/hashMinerData.txt");
        if(file.exists()&& file2.exists())
            initializeRunningQuarrys();

    }

    private void initializeRunningQuarrys() {
        for(Map.Entry mapElement:quarryThis.map.map.entrySet()){
            Location loc=(Location)mapElement.getKey();
                minerData miner=quarryThis.map.map.get(loc);
                miner.setContext(quarryThis);
                if(miner.isRunning){
                    quarryThis.runMiner(miner.quarryLocation,miner.chunk,miner.Id);
//
                }


        }
    }
//    mineCustom customMiner;
//    public void runCustom(Location quarryLoc){
//
//        this.customMiner=new mineCustom(this,quarryLoc);
//        Thread thread=new Thread(this.customMiner);
//        this.customMiner.setThread(thread);
//        thread.start();
//
//    }

    //makes thread for a miner
    mineChunk miner;
    public void runMiner(Location quarryLoc, Chunk breakChun,int id){
        this.miner=new mineChunk(quarryLoc,breakChun,this,id);
        Thread thread=new Thread(this.miner);
        this.miner.setThread(thread);
        thread.start();
    }

    //connects to main thread to updates blocks
    public void changeBlock(Chunk chunl,int x,int y,int z,Location quarryLocation){

        BukkitRunnable runner=new BukkitRunnable() {
            @Override
            public void run() {
                Block bloc=chunl.getBlock(x,y,z);


                try {

                    if (quarryThis.map.map.get(quarryLocation).getChestLocation()!=null&&quarryThis.map.map.get(quarryLocation).depositer.runningQuarryDepositor(bloc)) {

//                        synchronized (quarryThis.map.map.get(quarryLocation).depositer) {
//                            quarryThis.map.map.get(quarryLocation).depositer.notify();
//                        }
                    }else{
                        saveMinedItems(bloc, quarryLocation);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                detecBorderWater(chunl, x, y, z);
                bloc.setType(Material.AIR);
//                bloc.breakNaturally();

            }


        };runner.runTask(this);

    }
//    TODO make the border before any mining happens to prevent unmined water
    private void detecBorderWater(Chunk chunk,int x, int y, int z) {
        Block block;
        World world=chunk.getWorld();
        //border detection
        Location location=chunk.getBlock(x,y,z).getLocation();
        int worldX= (int) location.getX();
        int worldZ= (int) location.getZ();
        if(x==0&&z==0){
            block=world.getBlockAt(worldX-1,y,worldZ-1);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX-1,y,worldZ-1);
        }
        if (x==0){
            block=world.getBlockAt(worldX-1,y, worldZ);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX-1,y,worldZ);
        }
        if (x==15){
            block=world.getBlockAt(worldX+1,y,worldZ);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX+1,y,worldZ);
        }
        if (x==15&&z==0){
            block=world.getBlockAt(worldX+1,y,worldZ-1);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX+1,y,worldZ-1);
        }
        if (z==0){
            block=world.getBlockAt(worldX,y,worldZ-1);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX,y,worldZ-1);
        }
        if (z==15){
            block=world.getBlockAt(worldX,y,worldZ+1);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX,y,worldZ+1);
        }
        if (z==15&&x==0){
            block=world.getBlockAt(worldX-1,y,worldZ+1);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX-1,y,worldZ+1);
        }
        if(x==15&&z==15){
            block=world.getBlockAt(worldX+1,y,worldZ+1);
            if(block.isLiquid()){
                block.setType(Material.COBBLESTONE);
            }
            checkWaterLogged(chunk,worldX+1,y,worldZ+1);
        }

    }
    //Also checks for seaweed/kelp ect
    private void checkWaterLogged(Chunk chunk,int x,int y,int z) {
        boolean waterLogged=false;
        World world=chunk.getWorld();
        if (world.getBlockAt(x,y,z).getBlockData() instanceof Waterlogged){
            Waterlogged water=(Waterlogged) world.getBlockAt(x,y,z).getBlockData();
            waterLogged=water.isWaterlogged();
        }
        if (waterLogged){
            world.getBlockAt(x,y,z).breakNaturally();
            world.getBlockAt(x,y,z).setType(Material.COBBLESTONE);
        }
        checkWaterBlocks(chunk,x,y,z);


    }

    private void checkWaterBlocks(Chunk chunk, int x, int y, int z) {
        World world=chunk.getWorld();
        Material block=world.getBlockAt(x,y,z).getType();
        switch (block){
            case KELP:
            case SEAGRASS:
            case TALL_SEAGRASS:
            case SEA_PICKLE:
                world.getBlockAt(x,y,z).breakNaturally();
                world.getBlockAt(x,y,z).setType(Material.COBBLESTONE);
                break;
            default:
                ;

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        map.saveMap();

    }
    void saveMinedItems(Block bloc,Location quarryLocation) throws IOException {
        //trying to grab the miner thats running to update its list
        quarryThis.map.map.get(quarryLocation).savedItems.saveItemsToFile(quarryThis.map.map.get(quarryLocation).getintId(),bloc);
    }

    public void changeBlock(int x, int y, int z) {
        BukkitRunnable runner=new BukkitRunnable() {
            @Override
            public void run() {
                Block bloc=Bukkit.getServer().getWorld("world").getBlockAt(x,y,z);
//                Bukkit.getServer().getWorld("world").loadChunk(chunl);

                /*TODO add chest is full add items to a hidden list that will be added to the chest when
                   is removed */


                bloc.breakNaturally();
//                bloc.getWorld().unloadChunk(chunl);
//                bloc.getWorld().loadChunk(chunl);
            }
        };runner.runTask(this);

    }
}
